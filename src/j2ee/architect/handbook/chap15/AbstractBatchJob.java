package j2ee.architect.handbook.chap15;

import j2ee.architect.handbook.common.Logger;
import j2ee.architect.handbook.common.SampleAppRuntimeException;
import j2ee.architect.handbook.common.transaction.TransactionContext;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.StatefulJob;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Base class for batch jobs. This class does provide transaction
 * management.
 * @author D. Ashmore
 *
 */
public abstract class AbstractBatchJob implements StatefulJob {
	
	private Logger logger = new Logger(getClass().getName());

    @SuppressWarnings("rawtypes")
    private Map stateMap = null;
	
	/**
	 * Implementors expected to provide logic here.  Implementors can expect the following:
	 * 
	 * <li>All exceptions will be logged as critical via the Logger.</li>
	 * <li>Only one copy of a batch job will run at a time (enforced by scheduling product)</li>
	 * <li>Stateful Map property contains any parameters provided for the run of this batch job.</li>
	 *
	 * <p>Please throw some type of RuntimeException if you abend.
	 * <p>Implementors must provide a public null constructor.
	 * <p>Note that a proper Quartz configuration (quartz.properties file) must be in the application classpath.
	 */
	abstract public void execute();
	
	/**
	 * Provides a label used by the Scheduler for your application's batch jobs.
	 * 
	 * @return
	 */
	abstract protected String getJobGroupName();
	
	/**
	 * Needed by Quartz.
	 */
	public final void execute(JobExecutionContext context) throws JobExecutionException {
		this.execute(context.getMergedJobDataMap());
	}
	
	@SuppressWarnings("rawtypes")
    public final void execute(Map jobParmMap) throws JobExecutionException {
			
		try {
			logger.info("Batch job successfully started.  class={}", 
			        this.getClass().getName());
						
			this.stateMap = jobParmMap;

			new TransactionContext();
			this.execute();
			TransactionContext.getCurrent().commit();
			logger.info("Batch job successfully completed.  class={}", 
			        this.getClass().getName());
		}
		catch (Exception caughtException) {
		    TransactionContext.getCurrent().rollback();
		    
		    SampleAppRuntimeException sampleException = null;
			if (caughtException instanceof SampleAppRuntimeException ) {
				sampleException = (SampleAppRuntimeException)caughtException;
			}
			else if (caughtException instanceof JobExecutionException && 
					caughtException.getCause() instanceof SampleAppRuntimeException ) {
				sampleException = (SampleAppRuntimeException)caughtException.getCause();
			}
			else sampleException = new SampleAppRuntimeException(
			        "Batch Job failure", caughtException)			
    			.addContextValue("Batch class", this.getClass().getName());
			this.addContextValues(sampleException, jobParmMap);
			logger.error("Batch Job failure", sampleException);
			
		}

	}
	
	public void runImmediate() {
		this.runImmediate(null);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Provides applications a way to programatically trigger a job.
	 * @param parmMap
	 */
    public void runImmediate(Map parmMap) {
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler scheduler = sf.getScheduler();
			scheduler.start();

			JobDetail job = scheduler.getJobDetail(this.getClass().getName(), this.getJobGroupName());
			if (job == null)  {
				job = new JobDetail(this.getClass().getName(), this.getJobGroupName(), this.getClass());

				if (parmMap == null)  {
					parmMap = 	new HashMap();		
				}

				job.setJobDataMap(new JobDataMap(parmMap));
				scheduler.addJob(job, true);
			}
		
			scheduler.triggerJob(job.getName(), job.getGroup());

		} catch (SchedulerException schedulerException) {
		    SampleAppRuntimeException sampleException = new SampleAppRuntimeException(schedulerException)
			    .addContextValue("Batch class", this.getClass().getName());
		    
			this.addContextValues(sampleException, parmMap);
			logger.error("Batch Job failure", sampleException);
			
			throw sampleException;
		}
	}
	
	@SuppressWarnings("rawtypes")
    private void addContextValues(SampleAppRuntimeException e, Map map) {
	    if (map == null) {
	        return;
	    }
	    
	    for (Object key: map.keySet()) {
	        e.addContextValue(key.toString(), map.get(key));
	    }
	}
	
	protected Session getSession() {
		return TransactionContext.getCurrent().getSession();
	}
	
	@SuppressWarnings("rawtypes")
    protected Map getStateMap() {
		return stateMap;
	}

	protected Logger getLogger() {
        return logger;
    }

}

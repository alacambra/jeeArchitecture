package j2ee.architect.handbook.chap15;

import j2ee.architect.handbook.chap09.sample1.CustomerEntity;
import j2ee.architect.handbook.common.transaction.TransactionContext;
import j2ee.architect.handbook.common.util.HibernateTestUtils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestAbstractBatchJob {
    
    private TestJob testJob;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    	if (TransactionContext.getSessionFactory() == null) {
	        SessionFactory sessionFactory = HibernateTestUtils.basicSetup(new Class[]{CustomerEntity.class});
	        TransactionContext.setSessionFactory(sessionFactory);
    	}
        new TransactionContext();
    }

    @Before
    public void setUp() throws Exception {
        this.testJob = new TestJob();
    }

    @After
    public void tearDown() throws Exception {
        Thread.sleep(10000);
    }

    @Test
    public void testRunImmediate() throws Exception {
        int nbrRuns = TestJob.getNbrRuns();
        int nbrParms = TestJob.getNbrParms();
        
        try {this.testJob.runImmediate();}
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Job generated exception");
        }
        
        Thread.sleep(3000);
        Assert.assertTrue("Job not run", TestJob.getNbrRuns() > nbrRuns);
        Assert.assertTrue("Job not run", TestJob.getNbrParms() == nbrParms);
    }

    @Test
    public void testRunImmediateMap() throws Exception {
        int nbrRuns = TestJob.getNbrRuns();
        int nbrParms = TestJob.getNbrParms();
        
        Map<String,String> parmMap = new HashMap<String,String>();
        parmMap.put("parm1", "Derek");
        parmMap.put("parm2", "Ashmore");
        
        try {this.testJob.runImmediate(parmMap);}
        catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Job generated exception");
        }
        
        Thread.sleep(3000);
        Assert.assertTrue("Job not run", TestJob.getNbrRuns() > nbrRuns);
        Assert.assertTrue("Job not run", TestJob.getNbrParms() == nbrParms + 2);
    }
    
    public static class TestJob extends AbstractBatchJob {

        private static int nbrRuns = 0;
        private static int nbrParms = 0;

        @Override
        public void execute() {
            this.getLogger().info("test Job was run - here we are.");    
            nbrRuns++;
            nbrParms += this.getStateMap().size();
        }

        @Override
        protected String getJobGroupName() {
            return "test";
        }

        public static int getNbrRuns() {
            return nbrRuns;
        }

        public static int getNbrParms() {
            return nbrParms;
        }

    }

}

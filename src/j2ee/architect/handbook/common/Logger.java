package j2ee.architect.handbook.common;

import org.slf4j.LoggerFactory;

/**
 * Generic logger proxy used by custom application.
 * 
 *
 */
public class Logger implements java.io.Serializable
{
	
    public static final String DEFAULT_LOGGER_NAME = "GenericLogger";
    private static final long serialVersionUID = 5745230985246760618L;
 	
	private transient org.slf4j.Logger logger;
	private String loggerName = DEFAULT_LOGGER_NAME;
	
	public Logger () {
	    this(DEFAULT_LOGGER_NAME);
	}

	public Logger (String loggerName) {
		this.loggerName = loggerName;
		initLogger();
	}

    public void error(String messageFormat, Object ...argArray)	{
    	getLogger().error(messageFormat,argArray);
	}
    
    public boolean isInfo() {
    	return getLogger().isInfoEnabled();
    }

    public void info(String messageFormat, Object ...argArray)	{
    	getLogger().info(messageFormat, argArray);
	}
    
    public boolean isWarn() {
        return getLogger().isWarnEnabled();
    }
    
    public void warn(String messageFormat, Object ...argArray)  {
        getLogger().warn(messageFormat, argArray);
    }
    
    public boolean isDebug() {
    	return getLogger().isDebugEnabled();
    }
    
    public void debug(String messageFormat, Object ...argArray )	{
    	getLogger().debug(messageFormat, argArray);
	}

    public String getLoggerName() {
        return loggerName;
    }
    
    private void initLogger() {
        if (loggerName == null) loggerName = DEFAULT_LOGGER_NAME;
        logger = LoggerFactory.getLogger(loggerName);
    }
    
    private org.slf4j.Logger getLogger() {
        if (logger == null) {
            initLogger();
        }
        return logger;
    }
    
    
}
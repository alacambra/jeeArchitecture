package j2ee.architect.handbook.common;

import j2ee.architect.handbook.common.transaction.TransactionContext;

import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.Validate;

public abstract class BaseJDBCDAO {
    
    protected <T> T query(String sql, ResultSetHandler<T> resultSetHandler) {
        return this.query(sql, (Object[]) null, resultSetHandler);
    }
    
    protected <T> T query(String sql, Object[] parameters, ResultSetHandler<T> resultSetHandler) {
        Validate.notEmpty(sql, "Null or blank sql not allowed.");
        Validate.notNull(resultSetHandler, "Null resultSetHandler not allowed.");
        
        QueryRunner runner = new QueryRunner();
        try {return runner.query(this.getConnection(), sql, resultSetHandler, parameters);}
        catch (Exception e) {
            throw new SampleAppRuntimeException("Error running SQL query", e)
                .addContextValue("sql", sql)
                .addContextValue("parameters", parameters)
                .addContextValue("resultSetHandler", resultSetHandler.getClass().getName());
        }
    }
    
    protected int update(String sql) {
        return this.update(sql, (Object[]) null);
    }
    
    protected int update(String sql, Object...parameters) {
        Validate.notEmpty(sql, "Null or blank sql not allowed.");
        QueryRunner runner = new QueryRunner();
        try {return runner.update(this.getConnection(), sql, parameters);}
        catch (Exception e) {
            throw new SampleAppRuntimeException("Error running SQL", e)
                .addContextValue("sql", sql)
                .addContextValue("parameters", parameters);
        }
    }
    
    @SuppressWarnings("deprecation")
    private Connection getConnection() {
        return TransactionContext.getCurrent().getSession().connection();
    }

}

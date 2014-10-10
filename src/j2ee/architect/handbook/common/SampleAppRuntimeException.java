package j2ee.architect.handbook.common;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.commons.lang3.exception.ExceptionContext;

/**
 * Sample Application Runtime Exception; Thin extension of ContextedRuntimeException
 * in allowing us to more easily substitute a replacement if need be.
 * @author D. Ashmore
 *
 */
public class SampleAppRuntimeException extends ContextedRuntimeException {

    private static final long serialVersionUID = 4513590202386273350L;

    public SampleAppRuntimeException() {
        super();
    }

    public SampleAppRuntimeException(String message) {
        super(message);
    }

    public SampleAppRuntimeException(Throwable cause) {
        super(cause);
    }

    public SampleAppRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SampleAppRuntimeException(String message, Throwable cause,
            ExceptionContext context) {
        super(message, cause, context);
    }

    @Override
    public SampleAppRuntimeException addContextValue(String label, Object value) {
        super.addContextValue(label, value);
        return this;
    }

    @Override
    public ContextedRuntimeException setContextValue(String label, Object value) {
        super.setContextValue(label, value);
        return this;
    }

}

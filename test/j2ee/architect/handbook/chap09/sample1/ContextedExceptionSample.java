/*
 * Created on Aug 29, 2011
 *
 * <p> Copyright 2005, Delta Vortex Technologies, Inc.   All rights reserved.
 */
package j2ee.architect.handbook.chap09.sample1;

import java.io.IOException;

import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.junit.Before;
import org.junit.Test;

public class ContextedExceptionSample {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        IOException ioe = new IOException("unknown protocol: foo");
        
        Exception e =  new ContextedRuntimeException("Error finding customer", ioe)
            .addContextValue("Customer ID", 1234)
            .addContextValue("Customer Card Nbr", "1234-1234-1234-1234");
        
        e.printStackTrace();
    }

}

package com.igormaznitsa.jcp.cmdline;

import com.igormaznitsa.jcp.context.PreprocessorContext;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.*;

public class ClearDstDirectoryHandlerTest extends AbstractCommandLineHandlerTest{

    private static final ClearDstDirectoryHandler HANDLER = new ClearDstDirectoryHandler();

    @Override
    public void testThatTheHandlerInTheHandlerList() {
        assertHandlerInTheHandlerList(HANDLER);
    }

    @Override
    public void testExecution() throws Exception {
        final PreprocessorContext mock = mock(PreprocessorContext.class);
    
        assertFalse(HANDLER.processCommandLineKey("/c:", mock));
        assertFalse(HANDLER.processCommandLineKey("/CC", mock));
        assertFalse(HANDLER.processCommandLineKey("/C ", mock));
        verify(mock,never()).setClearDestinationDirBefore(anyBoolean());
    
        assertTrue(HANDLER.processCommandLineKey("/C", mock));
        verify(mock).setClearDestinationDirBefore(true);
        reset(mock);
        
        assertTrue(HANDLER.processCommandLineKey("/c", mock));
        verify(mock).setClearDestinationDirBefore(true);
        reset(mock);
    }
    
    @Override
    public void testName() {
        assertEquals("/C", HANDLER.getKeyName());
    }

    @Override
    public void testDescription() {
        assertDescription(HANDLER);
    }
}

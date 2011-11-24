package com.igormaznitsa.jcpreprocessor.expression;

import com.igormaznitsa.jcpreprocessor.context.PreprocessorContext;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExpressionTest {
    
    @Test
    public void testSimpleExpression() {
        final PreprocessorContext conext = new PreprocessorContext();
        assertEquals("Must be equal",Value.INT_TWO,Expression.evalExpression("40/4-2*4", conext));
    }
}

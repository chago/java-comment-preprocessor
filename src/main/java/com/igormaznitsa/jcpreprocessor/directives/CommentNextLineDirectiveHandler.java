package com.igormaznitsa.jcpreprocessor.directives;

import com.igormaznitsa.jcpreprocessor.cfg.PreprocessorContext;
import java.io.IOException;

public class CommentNextLineDirectiveHandler extends AbstractDirectiveHandler {

    @Override
    public String getName() {
        return "//";
    }

    @Override
    public boolean hasExpression() {
        return false;
    }

    @Override
    public DirectiveBehaviour execute(String string, ParameterContainer state, PreprocessorContext configurator) throws IOException {
         state.setCommentNextLine(true);
         return DirectiveBehaviour.NORMAL;
    }
 
    
}

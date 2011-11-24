/*
 * Copyright 2011 Igor Maznitsa (http://www.igormaznitsa.com)
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of version 3 of the GNU Lesser General Public
 * License as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307  USA
 */
package com.igormaznitsa.jcpreprocessor.directives;

import com.igormaznitsa.jcpreprocessor.context.PreprocessingState;
import com.igormaznitsa.jcpreprocessor.containers.PreprocessingFlag;
import com.igormaznitsa.jcpreprocessor.containers.TextFileDataContainer;
import com.igormaznitsa.jcpreprocessor.context.PreprocessorContext;

/**
 * The class implements the //#end directive
 * 
 * @author Igor Maznitsa (igor.maznitsa@igormaznitsa.com)
 */
public class EndDirectiveHandler extends AbstractDirectiveHandler {

    @Override
    public String getName() {
        return "end";
    }

    @Override
    public String getReference() {
        return "ends a "+DIRECTIVE_PREFIX+"while..."+DIRECTIVE_PREFIX+"end construction";
    }

    @Override
    public AfterDirectiveProcessingBehaviour execute(final String string, final PreprocessorContext context) {
         final PreprocessingState state = context.getPreprocessingState();
        if (state.isWhileStackEmpty()) {
            throw new IllegalStateException(DIRECTIVE_PREFIX+"end without "+DIRECTIVE_PREFIX+"while detected");
        }

        if (state.isDirectiveCanBeProcessedIgnoreBreak()) {
            final TextFileDataContainer thisWhile = state.peekWhile();
            final boolean breakIsSet = state.getPreprocessingFlags().contains(PreprocessingFlag.BREAK_COMMAND);
            state.popWhile();
            if (!breakIsSet) {
                state.goToString(thisWhile.getNextStringIndex());
            }
        } else {
            state.popWhile();
        }
        return AfterDirectiveProcessingBehaviour.PROCESSED;
    }

    @Override
    public boolean executeOnlyWhenExecutionAllowed() {
        return false;
    }

}

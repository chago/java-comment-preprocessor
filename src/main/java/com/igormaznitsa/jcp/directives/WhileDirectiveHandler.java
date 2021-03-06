/* 
 * Copyright 2014 Igor Maznitsa (http://www.igormaznitsa.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.jcp.directives;

import javax.annotation.Nonnull;

import com.igormaznitsa.jcp.containers.PreprocessingFlag;
import com.igormaznitsa.jcp.context.PreprocessingState;
import com.igormaznitsa.jcp.context.PreprocessorContext;
import com.igormaznitsa.jcp.expression.Expression;
import com.igormaznitsa.jcp.expression.Value;
import com.igormaznitsa.jcp.expression.ValueType;

/**
 * The class implements the //#while directive handler
 *
 * @author Igor Maznitsa (igor.maznitsa@igormaznitsa.com)
 */
public class WhileDirectiveHandler extends AbstractDirectiveHandler {

  @Override
  @Nonnull
  public String getName() {
    return "while";
  }

  @Override
  @Nonnull
  public String getReference() {
    return "start " + getFullName() + ".." + DIRECTIVE_PREFIX + "end loop structure";
  }

  @Override
  public boolean executeOnlyWhenExecutionAllowed() {
    return false;
  }

  @Override
  @Nonnull
  public DirectiveArgumentType getArgumentType() {
    return DirectiveArgumentType.BOOLEAN;
  }

  @Override
  @Nonnull
  public AfterDirectiveProcessingBehaviour execute(@Nonnull final String string, @Nonnull final PreprocessorContext context) {
    final PreprocessingState state = context.getPreprocessingState();

    if (state.isDirectiveCanBeProcessed()) {
      final Value condition = Expression.evalExpression(string, context);
      if (condition.getType() != ValueType.BOOLEAN) {
        throw context.makeException("Non boolean argument", null);
      }

      state.pushWhile(true);
      if (!condition.asBoolean()) {
        state.getPreprocessingFlags().add(PreprocessingFlag.BREAK_COMMAND);
      }
    } else {
      state.pushWhile(false);
    }

    return AfterDirectiveProcessingBehaviour.PROCESSED;
  }
}

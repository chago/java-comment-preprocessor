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
package com.igormaznitsa.jcp.cmdline;

import com.igormaznitsa.jcp.context.PreprocessorContext;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.Test;

public class AllowWhitespaceDirectiveHandlerTest extends AbstractCommandLineHandlerTest {

  private static final AllowWhitespaceDirectiveHandler HANDLER = new AllowWhitespaceDirectiveHandler();

  @Override
  public void testThatTheHandlerInTheHandlerList() {
    assertHandlerInTheHandlerList(HANDLER);
  }

  @Test
  public void testErrorWithoutFlag() {
  }
  
  @Override
  public void testExecution() throws Exception {
    final PreprocessorContext mock = preparePreprocessorContext();

    assertFalse(HANDLER.processCommandLineKey("/es:", mock));
    assertFalse(HANDLER.processCommandLineKey("/EK", mock));
    assertFalse(HANDLER.processCommandLineKey("/E ", mock));
    verify(mock, never()).setAllowWhitespace(anyBoolean());

    assertTrue(HANDLER.processCommandLineKey("/ES", mock));
    verify(mock).setAllowWhitespace(true);
    reset(mock);

    assertTrue(HANDLER.processCommandLineKey("/es", mock));
    verify(mock).setAllowWhitespace(true);
    reset(mock);
  }

  @Override
  public void testName() {
    assertEquals("/ES", HANDLER.getKeyName());
  }

  @Override
  public void testDescription() {
    assertDescription(HANDLER);
  }
}

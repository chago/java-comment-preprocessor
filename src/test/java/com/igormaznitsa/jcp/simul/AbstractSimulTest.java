/*
 * Copyright 2014 Igor Maznitsa.
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
package com.igormaznitsa.jcp.simul;

import com.igormaznitsa.jcp.JCPreprocessor;
import com.igormaznitsa.jcp.context.PreprocessorContext;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.rules.TemporaryFolder;

public abstract class AbstractSimulTest {

  protected TemporaryFolder tmpResultFolder;
  protected File sourceFolder;
  protected File etalonFolder;

  @Before
  public void before() throws Exception {

    final File testDir = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());

    final File base = new File(testDir, this.getClass().getName().replace('.', File.separatorChar));

    final File simulfolder = new File(testDir.getParentFile(), "simultests");
    simulfolder.mkdirs();

    tmpResultFolder = new TemporaryFolder(simulfolder);
    tmpResultFolder.create();

    sourceFolder = new File(base, "src");
    etalonFolder = new File(base, "etl");
  }

  @After
  public void after() throws Exception {
    if (deleteResult()) {
      try {
        FileUtils.cleanDirectory(tmpResultFolder.getRoot());
      }
      finally {
        tmpResultFolder.delete();
      }
    }
  }

  public boolean deleteResult() {
    return true;
  }

  public abstract void check(PreprocessorContext context, JCPreprocessor.PreprocessingStatistics stat) throws Exception;

  private void assertFolder(final File folder1, final File folder2) throws Exception {
    for(final File f : folder1.listFiles()){
      final File f2 = new File(folder2,f.getName());
      if (!f2.exists()){
        fail("Doesn't exist :"+f2.getAbsolutePath());
      }
      if (f.isFile() && !f2.isFile()){
        fail("Must be file : "+f2.getAbsolutePath());
      }else
      if (f.isDirectory()){
        if (!f2.isDirectory())
        fail("Must be file : " + f2.getAbsolutePath());
        else assertFolder(f, f2);
      }else{
        assertEquals("File size must be the same",f.length(),f2.length());
        assertEquals("Checksum must be equal",FileUtils.checksumCRC32(f),FileUtils.checksumCRC32(f2));
      }
    }
  }
  
  @Test
  public final void main() throws Exception {
    final PreprocessorContext context = new PreprocessorContext();
    context.setClearDestinationDirBefore(true);
    context.setSourceDirectories(sourceFolder.getAbsolutePath());
    context.setDestinationDirectory(tmpResultFolder.getRoot().getAbsolutePath());
    context.setExcludedFileExtensions("xml");
    context.setVerbose(true);
    System.setProperty("jcp.line.separator", "\n");

    JCPreprocessor preprocessor = new JCPreprocessor(context);
    final JCPreprocessor.PreprocessingStatistics stat = preprocessor.execute();

    assertFolder(etalonFolder, tmpResultFolder.getRoot());
    
    check(context,stat);
  }
}
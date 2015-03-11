/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.internal.codegen.writer;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.Filer;
import javax.tools.JavaFileObject;
import org.jetbrains.annotations.NotNull;

public class TypeSpecWriter {

  @NotNull
  private final Filer mFiler;

  public TypeSpecWriter(@NotNull final Filer filer) {
    mFiler = filer;
  }

  /**
   * Writes the TypeSpec to file.
   *
   * @param typeSpec The TypeSpec to write.
   *
   * @throws IOException if an I/O error occurred.
   */
  public void writeToFile(@NotNull final String packageName, @NotNull final TypeSpec typeSpec) throws IOException {
    JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();

    JavaFileObject sourceFile = mFiler.createSourceFile(typeSpec.name);
    try (Writer writer = sourceFile.openWriter()) {
      javaFile.writeTo(writer);
    }
  }
}

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
      //writer.flush();
    }
  }
}

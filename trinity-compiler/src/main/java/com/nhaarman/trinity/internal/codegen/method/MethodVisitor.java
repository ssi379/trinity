package com.nhaarman.trinity.internal.codegen.method;

import org.jetbrains.annotations.NotNull;

public interface MethodVisitor {

  void visit(@NotNull FindMethod findMethod);

  void visit(@NotNull CreateMethod createMethod);

  void visit(@NotNull FindAllMethod findAllMethod);
}

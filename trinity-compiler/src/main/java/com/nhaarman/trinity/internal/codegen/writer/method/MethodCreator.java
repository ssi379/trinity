package com.nhaarman.trinity.internal.codegen.writer.method;

import com.squareup.javapoet.MethodSpec;

public interface MethodCreator {
  MethodSpec create();
}

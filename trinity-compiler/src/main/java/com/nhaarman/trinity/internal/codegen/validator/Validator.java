package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.ProcessingException;

public interface Validator<T> {

  void validate(T t) throws ProcessingException;
}

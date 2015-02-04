package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import java.util.Collection;
import javax.annotation.processing.Messager;

public class RepositoryClassValidator {

  private final Messager mMessager;

  public RepositoryClassValidator(final Messager messager) {
    mMessager = messager;
  }

  public boolean validate(final Collection<RepositoryClass> repositoryClasses) {
    boolean result = true;

    for (RepositoryClass repositoryClass : repositoryClasses) {
      result &= validate(repositoryClass);
    }

    return result;
  }

  private boolean validate(final RepositoryClass repositoryClass) {
    return true;
  }
}

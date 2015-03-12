package com.nhaarman.trinity.internal.codegen.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class RepositoryClassRepository {

  @NotNull
  private final Map<String, RepositoryClass> mRepositoryClasses;

  public RepositoryClassRepository() {
    mRepositoryClasses = new HashMap<>();
  }

  public void save(@NotNull final RepositoryClass repositoryClass) {
    String key = repositoryClass.getFullyQualifiedName();
    mRepositoryClasses.put(key, repositoryClass);
  }

  public void save(@NotNull final Collection<RepositoryClass> repositoryClasses) {
    for (RepositoryClass repositoryClass : repositoryClasses) {
      save(repositoryClass);
    }
  }

  @NotNull
  public Collection<RepositoryClass> all() {
    return mRepositoryClasses.values();
  }

  public void clear() {
    mRepositoryClasses.clear();
  }
}

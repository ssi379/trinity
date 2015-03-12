package com.nhaarman.trinity.internal.codegen.data;

import org.jetbrains.annotations.NotNull;

public class RepositoryGateway {

  @NotNull
  private final TableClassRepository mTableClassRepository;

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final RepositoryClassRepository mRepositoryClassRepository;

  public RepositoryGateway() {
    mTableClassRepository = new TableClassRepository();
    mColumnMethodRepository = new ColumnMethodRepository();
    mRepositoryClassRepository = new RepositoryClassRepository();
  }

  @NotNull
  public TableClassRepository getTableClassRepository() {
    return mTableClassRepository;
  }

  @NotNull
  public ColumnMethodRepository getColumnMethodRepository() {
    return mColumnMethodRepository;
  }

  @NotNull
  public RepositoryClassRepository getRepositoryClassRepository() {
    return mRepositoryClassRepository;
  }
}

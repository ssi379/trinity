package com.nhaarman.trinity.query;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Executable {

  @NotNull
  String getSql();

  @Nullable
  String[] getArguments();
}

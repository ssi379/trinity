package com.nhaarman.trinity;

import org.jetbrains.annotations.NotNull;

public interface TypeSerializer<D, S> {

  @NotNull
  S serialize(@NotNull D deserializedObject);

  @NotNull
  D deserialize(@NotNull S serializedObject);
}


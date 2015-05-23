package com.nhaarman.trinity.internal.codegen.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SerializerClassRepository {

  @NotNull
  private final Map<String, SerializerClass> mSerializerClasses;

  public SerializerClassRepository() {
    mSerializerClasses = new HashMap<>();
  }

  public void store(@NotNull final SerializerClass serializerClass) {
    String key = serializerClass.getFullyQualifiedSerializableTypeName();
    mSerializerClasses.put(key, serializerClass);
  }

  public void store(@NotNull final Collection<SerializerClass> serializerClasses) {
    for (SerializerClass serializerClass : serializerClasses) {
      store(serializerClass);
    }
  }

  @NotNull
  public Collection<SerializerClass> all() {
    return mSerializerClasses.values();
  }

  public void clear() {
    mSerializerClasses.clear();
  }

  @Nullable
  public SerializerClass findByFullyQualifiedSerializableTypeName(@NotNull final String type) {
    return mSerializerClasses.get(type);
  }
}

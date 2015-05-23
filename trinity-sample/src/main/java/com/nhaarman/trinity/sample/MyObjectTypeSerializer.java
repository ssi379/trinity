package com.nhaarman.trinity.sample;

import com.nhaarman.trinity.TypeSerializer;
import com.nhaarman.trinity.annotations.Serializer;
import org.jetbrains.annotations.NotNull;

@Serializer(MyObject.class)
public class MyObjectTypeSerializer implements TypeSerializer<MyObject, String> {

  @NotNull
  @Override
  public String serialize(@NotNull final MyObject deserializedObject) {
    return deserializedObject.getS();
  }

  @NotNull
  @Override
  public MyObject deserialize(@NotNull final String serializedObject) {
    return new MyObject(serializedObject);
  }
}

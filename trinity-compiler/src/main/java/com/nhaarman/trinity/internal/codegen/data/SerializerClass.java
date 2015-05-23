package com.nhaarman.trinity.internal.codegen.data;

import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SerializerClass {

  @NotNull
  private final String mClassName;

  @Nullable
  private final String mPackageName;

  @NotNull
  private final String mFullyQualifiedSerializableTypeName;

  @NotNull
  private final Element mElement;

  public SerializerClass(@NotNull final String className,
                         @Nullable final String packageName,
                         @NotNull final String fullyQualifiedSerializableTypeName,
                         @NotNull final Element element) {
    mClassName = className;
    mPackageName = packageName;
    mFullyQualifiedSerializableTypeName = fullyQualifiedSerializableTypeName;
    mElement = element;
  }

  @NotNull
  public String getFullyQualifiedClassName() {
    return mPackageName + '.' + mClassName;
  }

  @NotNull
  public String getFullyQualifiedSerializableTypeName() {
    return mFullyQualifiedSerializableTypeName;
  }

  public static class Builder {

    private String mClassName;
    private String mPackageName;
    private Element mSerializerElement;
    private String mFullyQualifiedSerializableTypeName;

    @NotNull
    public Builder withClassName(@NotNull final String className) {
      mClassName = className;
      return this;
    }

    @NotNull
    public Builder withPackageName(@NotNull final String packageName) {
      mPackageName = packageName;
      return this;
    }

    @NotNull
    public Builder withFullyQualifiedSerializableTypeName(@NotNull final String fullyQualifiedSerializableTypeName) {
      mFullyQualifiedSerializableTypeName = fullyQualifiedSerializableTypeName;
      return this;
    }

    @NotNull
    public Builder withElement(@NotNull final Element serializerElement) {
      mSerializerElement = serializerElement;
      return this;
    }

    public SerializerClass build() {
      return new SerializerClass(
          mClassName,
          mPackageName,
          mFullyQualifiedSerializableTypeName,
          mSerializerElement
      );
    }
  }
}

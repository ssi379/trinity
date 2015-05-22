package com.nhaarman.trinity.internal.codegen;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * An enum which declares wich column types are supported.
 * The Visitor Pattern is used to avoid switch statements with missing branches.
 * http://stackoverflow.com/a/859751/675383
 */
public enum SupportedColumnType {

  INT("int") {
    @Override
    public <T> T accept(final SupportedColumnTypeVisitor<T> visitor) {
      return visitor.acceptInt();
    }
  },
  JAVA_LANG_INTEGER("java.lang.Integer") {
    @Override
    public <T> T accept(final SupportedColumnTypeVisitor<T> visitor) {
      return visitor.acceptJavaLangInteger();
    }
  },
  LONG("long") {
    @Override
    public <T> T accept(final SupportedColumnTypeVisitor<T> visitor) {
      return visitor.acceptLong();
    }
  },
  JAVA_LANG_LONG("java.lang.Long") {
    @Override
    public <T> T accept(final SupportedColumnTypeVisitor<T> visitor) {
      return visitor.acceptJavaLangLong();
    }
  },
  BOOLEAN("boolean") {
    @Override
    public <T> T accept(final SupportedColumnTypeVisitor<T> visitor) {
      return visitor.acceptBoolean();
    }
  },
  JAVA_LANG_BOOLEAN("java.lang.Boolean") {
    @Override
    public <T> T accept(final SupportedColumnTypeVisitor<T> visitor) {
      return visitor.acceptJavaLangBoolean();
    }
  },
  JAVA_LANG_STRING("java.lang.String") {
    @Override
    public <T> T accept(final SupportedColumnTypeVisitor<T> visitor) {
      return visitor.acceptJavaLangString();
    }
  };

  private static final Map<String, SupportedColumnType> TYPES;

  static {
    TYPES = new HashMap<>();

    for (SupportedColumnType supportedColumnType : values()) {
      TYPES.put(supportedColumnType.mFullyQualifiedName, supportedColumnType);
    }
  }

  private final String mFullyQualifiedName;

  SupportedColumnType(@NonNls @NotNull final String fullyQualifiedName) {
    mFullyQualifiedName = fullyQualifiedName;
  }

  public static SupportedColumnType from(@NotNull final String fullyQualifiedName) {
    return TYPES.get(fullyQualifiedName);
  }

  public abstract <T> T accept(SupportedColumnTypeVisitor<T> visitor);

  public interface SupportedColumnTypeVisitor<T> {

    @NotNull
    T acceptInt();

    @NotNull
    T acceptJavaLangInteger();

    @NotNull
    T acceptLong();

    @NotNull
    T acceptJavaLangLong();

    @NotNull
    T acceptBoolean();

    @NotNull
    T acceptJavaLangBoolean();

    @NotNull
    T acceptJavaLangString();
  }
}

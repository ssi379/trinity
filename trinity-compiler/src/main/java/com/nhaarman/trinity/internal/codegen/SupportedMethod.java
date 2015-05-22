package com.nhaarman.trinity.internal.codegen;

import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An enum which declares wich repository methods are supported.
 * The Visitor Pattern is used to avoid switch statements with missing branches.
 * http://stackoverflow.com/a/859751/675383
 */
public enum SupportedMethod {

  FIND("find") {
    @Override
    public <T> T accept(@NotNull final SupportedMethodVisitor<T> visitor) {
      return visitor.visitFind();
    }
  },
  FIND_BY_ID("findById") {
    @Override
    public <T> T accept(@NotNull final SupportedMethodVisitor<T> visitor) {
      return visitor.visitFind();
    }
  },
  CREATE("create") {
    @Override
    public <T> T accept(@NotNull final SupportedMethodVisitor<T> visitor) {
      return visitor.visitCreate();
    }
  },
  FIND_ALL("findAll") {
    @Override
    public <T> T accept(@NotNull final SupportedMethodVisitor<T> visitor) {
      return visitor.visitFindAll();
    }
  };

  private static final Map<String, SupportedMethod> SUPPORTED_METHODS;

  static {
    SUPPORTED_METHODS = new HashMap<>();

    for (SupportedMethod supportedMethod : values()) {
      SUPPORTED_METHODS.put(supportedMethod.mMethodName, supportedMethod);
    }
  }

  private final String mMethodName;

  SupportedMethod(@NonNls final String methodName) {
    mMethodName = methodName;
  }

  @Nullable
  public static SupportedMethod from(@NotNull final String methodName) {
    return SUPPORTED_METHODS.get(methodName);
  }

  public abstract <T> T accept(@NotNull SupportedMethodVisitor<T> visitor);

  public interface SupportedMethodVisitor<T> {

    @NotNull
    T visitFind();

    @NotNull
    T visitCreate();

    @NotNull
    T visitFindAll();
  }
}

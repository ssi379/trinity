package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class MethodCreatorFactory {

  @NotNull
  private final RepositoryClass mRepositoryClass;

  @NotNull
  private final FieldSpec mDatabaseFieldSpec;

  @NotNull
  private final MethodSpec mReadCursorSpec;

  @NotNull
  private final MethodSpec mCreateContentValuesSpec;

  public MethodCreatorFactory(@NotNull final RepositoryClass repositoryClass,
                              @NotNull final FieldSpec databaseFieldSpec,
                              @NotNull final MethodSpec readCursorSpec,
                              @NotNull final MethodSpec createContentValuesSpec) {
    mRepositoryClass = repositoryClass;
    mDatabaseFieldSpec = databaseFieldSpec;
    mReadCursorSpec = readCursorSpec;
    mCreateContentValuesSpec = createContentValuesSpec;
  }

  public MethodCreator creatorFor(@NotNull final RepositoryMethod method) {
    switch (method.getMethodName().toLowerCase(Locale.ENGLISH)) {
      case "find":
      case "findbyid":
        return new FindMethodCreator(mRepositoryClass, mDatabaseFieldSpec, mReadCursorSpec, method);
      case "create":
        return new CreateMethodCreator(mRepositoryClass, mCreateContentValuesSpec, method);
      default:
        // TODO: Use Messager
        throw new UnsupportedOperationException(String.format("Cannot implement %s: unknown method.", method.getMethodName()));
    }
  }
}

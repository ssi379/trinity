package com.nhaarman.trinity.internal.codegen.table.repository.writer;

import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.table.repository.RepositoryMethod;
import com.squareup.javapoet.MethodSpec;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class CreatorFactory {

  @NotNull
  private final RepositoryClass mRepositoryClass;

  @NotNull
  private final MethodSpec mReadCursorSpec;

  @NotNull
  private final MethodSpec mCreateContentValuesSpec;

  public CreatorFactory(@NotNull final RepositoryClass repositoryClass,
                        @NotNull final MethodSpec readCursorSpec,
                        @NotNull final MethodSpec createContentValuesSpec) {
    mRepositoryClass = repositoryClass;
    mReadCursorSpec = readCursorSpec;
    mCreateContentValuesSpec = createContentValuesSpec;
  }

  public MethodCreator creatorFor(@NotNull final RepositoryMethod method) {
    switch (method.getMethodName().toLowerCase(Locale.ENGLISH)) {
      case "find":
      case "findbyid":
        return new FindCreator(mRepositoryClass, mReadCursorSpec, method);
      case "create":
        return new CreateCreator(mRepositoryClass, mCreateContentValuesSpec, method);
      default:
        // TODO: Use Messager
        throw new UnsupportedOperationException(String.format("Cannot implement %s: unknown method.", method.getMethodName()));
    }
  }
}

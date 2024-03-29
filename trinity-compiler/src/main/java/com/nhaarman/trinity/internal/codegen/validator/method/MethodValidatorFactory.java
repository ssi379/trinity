package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.SupportedMethod;
import com.nhaarman.trinity.internal.codegen.SupportedMethod.SupportedMethodVisitor;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MethodValidatorFactory {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final RepositoryClass mRepositoryClass;

  @NotNull
  private final MySupportedMethodVisitor mMySupportedMethodVisitor;

  public MethodValidatorFactory(@NotNull final ColumnMethodRepository columnMethodRepository, @NotNull final RepositoryClass repositoryClass) {
    mColumnMethodRepository = columnMethodRepository;
    mRepositoryClass = repositoryClass;
    mMySupportedMethodVisitor = new MySupportedMethodVisitor();
  }

  @Nullable
  public Validator<RepositoryMethod> methodValidator(@NotNull final RepositoryMethod repositoryMethod) {
    SupportedMethod supportedMethod = SupportedMethod.from(repositoryMethod.getMethodName());
    if (supportedMethod == null) {
      return null;
    }

    return supportedMethod.accept(mMySupportedMethodVisitor);
  }

  private class MySupportedMethodVisitor implements SupportedMethodVisitor<Validator<RepositoryMethod>> {

    @NotNull
    @Override
    public Validator<RepositoryMethod> visitFind() {
      return new FindMethodValidator(mColumnMethodRepository, mRepositoryClass);
    }

    @NotNull
    @Override
    public Validator<RepositoryMethod> visitFindAll() {
      return new FindAllMethodValidator();
    }

    @NotNull
    @Override
    public Validator<RepositoryMethod> visitCreate() {
      return new CreateMethodValidator();
    }

    @NotNull
    @Override
    public Validator<RepositoryMethod> visitCreateAll() {
      return new CreateAllMethodValidator();
    }
  }
}

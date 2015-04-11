package com.nhaarman.trinity.internal.codegen.validator.method;

import com.nhaarman.trinity.internal.codegen.ValidationException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryMethod;
import com.nhaarman.trinity.internal.codegen.validator.Validator;
import org.jetbrains.annotations.NotNull;

public class FindMethodValidator implements Validator<RepositoryMethod> {

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final RepositoryClass mRepositoryClass;

  public FindMethodValidator(@NotNull final ColumnMethodRepository columnMethodRepository,
                             @NotNull final RepositoryClass repositoryClass) {
    mColumnMethodRepository = columnMethodRepository;
    mRepositoryClass = repositoryClass;
  }

  @Override
  public void validate(@NotNull final RepositoryMethod method) throws ValidationException {
    ColumnMethod primaryKeyGetter = mColumnMethodRepository.findPrimaryKeyGetter(mRepositoryClass.getTableClassFullyQualifiedName());
    ColumnMethod primaryKeySetter = mColumnMethodRepository.findPrimaryKeySetter(mRepositoryClass.getTableClassFullyQualifiedName());

    if (primaryKeyGetter == null && primaryKeySetter == null) {
      throw new ValidationException(String.format("Generating a '%s' implementation requires at least one method in %s to be annotated with @PrimaryKey.", method.getMethodName(),
          mRepositoryClass.getTableClassFullyQualifiedName()), method.getElement());
    }

    if (primaryKeyGetter != null && !primaryKeyGetter.getType().equals(method.getParameter().getType())) {
      throw new ValidationException(
          String.format(
              "Type '%s' of method '%s' does not match with type '%s' of method '%s.%s'",
              method.getParameter().getType(),
              method.getMethodName(),
              primaryKeyGetter.getType(),
              primaryKeyGetter.getFullyQualifiedTableClassName(),
              primaryKeyGetter.getMethodName()
          ),
          method.getElement()
      );
    }

    if (primaryKeySetter != null && !primaryKeySetter.getType().equals(method.getParameter().getType())) {
      throw new ValidationException(
          String.format(
              "Type '%s' of method '%s' does not match with type '%s' of method '%s.%s'",
              method.getParameter().getType(),
              method.getMethodName(),
              primaryKeySetter.getType(),
              primaryKeySetter.getFullyQualifiedTableClassName(),
              primaryKeySetter.getMethodName()
          ),
          method.getElement()
      );
    }
  }
}

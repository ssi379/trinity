package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.Message;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.SupportedColumnType;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import com.nhaarman.trinity.internal.codegen.data.SerializerClass;
import com.nhaarman.trinity.internal.codegen.data.SerializerClassRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.ERROR;
import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class ColumnMethodValidator implements Validator<Collection<ColumnMethod>> {

  @NotNull
  private final SerializerClassRepository mSerializerClassRepository;

  public ColumnMethodValidator(@NotNull final SerializerClassRepository serializerClassRepository) {
    mSerializerClassRepository = serializerClassRepository;
  }

  @NotNull
  @Override
  public ProcessingStepResult validate(@NotNull final Collection<ColumnMethod> columnMethods, @NotNull final ValidationHandler validationHandler) {
    Collection<ColumnMethod> getters = new HashSet<>(columnMethods.size());
    Collection<ColumnMethod> setters = new HashSet<>(columnMethods.size());

    for (ColumnMethod columnMethod : columnMethods) {
      if (columnMethod.isGetter()) {
        getters.add(columnMethod);
      } else {
        setters.add(columnMethod);
      }
    }

    return validate(getters, setters, validationHandler);
  }

  @NotNull
  private ProcessingStepResult validate(@NotNull final Collection<ColumnMethod> getters,
                                        @NotNull final Collection<ColumnMethod> setters,
                                        @NotNull final ValidationHandler validationHandler) {
    ProcessingStepResult result = OK;

    result = result.and(validateUniqueGetters(getters, validationHandler));
    result = result.and(validateUniqueSetters(setters, validationHandler));
    result = result.and(validateEqualGetterAndSetterTypes(getters, setters, validationHandler));
    result = result.and(validateColumnTypes(getters, validationHandler));
    result = result.and(validateColumnTypes(setters, validationHandler));

    return result;
  }

  /**
   * Validates whether there is a maximum of 1 getter per column.
   *
   * @param getters The getters to check.
   */
  @NotNull
  private ProcessingStepResult validateUniqueGetters(@NotNull final Collection<ColumnMethod> getters, final ValidationHandler validationHandler) {
    Set<String> getterNames = new HashSet<>();
    for (ColumnMethod getter : getters) {
      if (!getterNames.add(getter.getColumnName())) {
        validationHandler.onError(getter.getElement(), null, Message.MULTIPLE_GETTERS_FOR_SAME_COLUMN);
        return ERROR;
      }
    }

    return OK;
  }

  /**
   * Validates whether there is a maximum of 1 setter per column.
   *
   * @param setters The setters to check.
   */
  @NotNull
  private ProcessingStepResult validateUniqueSetters(@NotNull final Collection<ColumnMethod> setters, final ValidationHandler validationHandler) {
    Set<String> setterNames = new HashSet<>();
    for (ColumnMethod setter : setters) {
      if (!setterNames.add(setter.getColumnName())) {
        validationHandler.onError(setter.getElement(), null, Message.MULTIPLE_SETTERS_FOR_SAME_COLUMN);
        return ERROR;
      }
    }

    return OK;
  }

  /**
   * Validates whether the type of the getter and setter for a single column have the same type.
   *
   * @param getters The getters to check.
   * @param setters The setters to check.
   */
  @NotNull
  private ProcessingStepResult validateEqualGetterAndSetterTypes(@NotNull final Collection<ColumnMethod> getters,
                                                                 @NotNull final Collection<ColumnMethod> setters,
                                                                 @NotNull final ValidationHandler validationHandler) {
    ProcessingStepResult result = OK;

    for (ColumnMethod getter : getters) {
      for (ColumnMethod setter : setters) {
        if (getter.getColumnName().equals(setter.getColumnName())) {
          result = result.and(validateEqualGetterAndSetterType(getter, setter, validationHandler));
        }
      }
    }

    return result;
  }

  /**
   * Validates whether the type of the getter and setter have the same type.
   *
   * @param getter The getter to check.
   * @param setter The setter to check.
   */
  @NotNull
  private ProcessingStepResult validateEqualGetterAndSetterType(@NotNull final ColumnMethod getter,
                                                                @NotNull final ColumnMethod setter,
                                                                @NotNull final ValidationHandler validationHandler) {
    if (!getter.getType().equals(setter.getType())) {
      validationHandler.onError(
          getter.getElement(),
          null,
          Message.GETTER_AND_SETTER_TYPE_MISMATCH,
          getter.getColumnName()
      );
      return ERROR;
    }

    return OK;
  }

  @NotNull
  private ProcessingStepResult validateColumnTypes(@NotNull final Collection<ColumnMethod> columnMethods, @NotNull final ValidationHandler validationHandler) {
    ProcessingStepResult result = OK;

    for (ColumnMethod columnMethod : columnMethods) {
      result = result.and(validateColumnType(columnMethod, validationHandler));
    }

    return result;
  }

  @NotNull
  private ProcessingStepResult validateColumnType(@NotNull final ColumnMethod columnMethod, @NotNull final ValidationHandler validationHandler) {
    String type = columnMethod.getType();

    SerializerClass serializerClass = mSerializerClassRepository.findByFullyQualifiedSerializableTypeName(type);
    if (serializerClass != null) {
      return OK;
    }

    if (SupportedColumnType.from(type) == null) {
      validationHandler.onError(columnMethod.getElement(), null, Message.UNSUPPORTED_COLUMN_TYPE, type);
      return ERROR;
    }

    return OK;
  }
}

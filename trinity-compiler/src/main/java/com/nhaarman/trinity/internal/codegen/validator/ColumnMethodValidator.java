package com.nhaarman.trinity.internal.codegen.validator;

import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class ColumnMethodValidator {

  public void validate(@NotNull final Collection<ColumnMethod> getters, @NotNull final Collection<ColumnMethod> setters) throws ProcessingException {
    validateUniqueGetters(getters);
    validateUniqueSetters(setters);
    validateEqualGetterAndSetterTypes(getters, setters);
  }

  /**
   * Validates whether there is a maximum of 1 getter per column.
   *
   * @param getters The getters to check.
   *
   * @throws ProcessingException if a second getter is found for a single column.
   */
  private void validateUniqueGetters(@NotNull final Collection<ColumnMethod> getters) throws ProcessingException {
    Set<String> getterNames = new HashSet<>();
    for (ColumnMethod getter : getters) {
      if (!getterNames.add(getter.getColumnName())) {
        throw new ProcessingException("Table class cannot contain multiple getters for the same column", getter.getElement());
      }
    }
  }

  /**
   * Validates whether there is a maximum of 1 setter per column.
   *
   * @param setters The setters to check.
   *
   * @throws ProcessingException if a second setter is found for a single column.
   */
  private void validateUniqueSetters(@NotNull final Collection<ColumnMethod> setters) throws ProcessingException {
    Set<String> setterNames = new HashSet<>();
    for (ColumnMethod setter : setters) {
      if (!setterNames.add(setter.getColumnName())) {
        throw new ProcessingException("Table class cannot contain multiple setters for the same column", setter.getElement());
      }
    }
  }

  /**
   * Validates whether the type of the getter and setter for a single column have the same type.
   *
   * @param getters The getters to check.
   * @param setters The setters to check.
   *
   * @throws ProcessingException if a getter and setter has been found for the same column with a different type.
   */
  private void validateEqualGetterAndSetterTypes(@NotNull final Collection<ColumnMethod> getters,
                                                 @NotNull final Collection<ColumnMethod> setters) throws ProcessingException {
    for (ColumnMethod getter : getters) {
      for (ColumnMethod setter : setters) {
        if (getter.getColumnName().equals(setter.getColumnName())) {
          validateEqualGetterAndSetterType(getter, setter);
        }
      }
    }
  }

  /**
   * Validates whether the type of the getter and setter have the same type.
   *
   * @param getter The getter to check.
   * @param setter The setter to check.
   *
   * @throws ProcessingException if the getter and setter have a different type.
   */
  private void validateEqualGetterAndSetterType(@NotNull final ColumnMethod getter,
                                                @NotNull final ColumnMethod setter) throws ProcessingException {
    if (!getter.getType().equals(setter.getType())) {
      throw new ProcessingException(
          String.format("Getter and setter for column with name \"%s\" have different types.", getter.getColumnName()),
          getter.getElement()
      );
    }
  }
}

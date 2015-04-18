package com.nhaarman.trinity.internal.codegen;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import org.jetbrains.annotations.NotNull;

/**
 * An enum for displaying messages to the user.
 * See http://stackoverflow.com/a/4491223/675383.
 */
@SuppressWarnings("EnumeratedConstantNamingConvention")
public enum Message {

  /* Validation */

  TABLE_ANNOTATION_CAN_ONLY_BE_APPLIED_TO_CLASSES,
  COLUMN_ANNOTATION_CAN_ONLY_BE_APPLIED_TO_METHODS,
  REPOSITORY_ANNOTATION_CAN_ONLY_BE_APPLIED_TO_CLASSES_OR_INTERFACES,

  UNSUPPORTED_METHOD_NAME,
  CANNOT_CREATE_TWO_TABLES_WITH_THE_SAME_NAME,
  MISSING_PRIMARYKEY_METHOD_FOR_FIND_IMPLEMENTATION,
  PRIMARYKEY_FIND_TYPE_MISMATCH,
  MULTIPLE_GETTERS_FOR_SAME_COLUMN,
  MULTIPLE_SETTERS_FOR_SAME_COLUMN,
  GETTER_AND_SETTER_TYPE_MISMATCH;

  /**
   * Resources for the default locale
   */
  private static final ResourceBundle RES = ResourceBundle.getBundle("MessagesBundle");

  /**
   * @return the locale-dependent message
   */
  public String toLocalizedString(@NotNull final String... args) {
    MessageFormat formatter = new MessageFormat("");//, Locale.getDefault());
    formatter.applyPattern(RES.getString(name()));
    return formatter.format(args);
  }
}

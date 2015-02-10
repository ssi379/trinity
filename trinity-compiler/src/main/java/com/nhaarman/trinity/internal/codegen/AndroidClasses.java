package com.nhaarman.trinity.internal.codegen;

import com.squareup.javapoet.ClassName;

/**
 * A convenience class which describes the fully qualified type names of common Android classes.
 */
@SuppressWarnings("HardCodedStringLiteral")
public class AndroidClasses {

  public static final ClassName CONTENT_VALUES = ClassName.get("android.content", "ContentValues");

  public static final ClassName CURSOR = ClassName.get("android.database", "Cursor");

  public static final ClassName SQLITE_DATABASE = ClassName.get("android.database.sqlite", "SQLiteDatabase");

  private AndroidClasses() {
  }
}

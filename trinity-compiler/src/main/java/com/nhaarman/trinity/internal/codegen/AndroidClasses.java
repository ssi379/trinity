/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

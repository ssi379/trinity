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

package com.nhaarman.trinity.internal.codegen.data;

import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class that holds information about a class that is annotated with the @Table annotation.
 */
public class TableClass {

  @NotNull
  private final String mClassName;

  @NotNull
  private final String mPackageName;

  @NotNull
  private final String mTableName;

  @NotNull
  private final Element mElement;

  public TableClass(@NotNull final String tableName,
                    @NotNull final String className,
                    @NotNull final String packageName,
                    @NotNull final Element element) {

    mTableName = tableName;
    mClassName = className;
    mPackageName = packageName;
    mElement = element;
  }

  @NotNull
  public String getTableName() {
    return mTableName;
  }

  @NotNull
  public Element getElement() {
    return mElement;
  }

  @NotNull
  public String getClassName() {
    return mClassName;
  }

  @NotNull
  public String getPackageName() {
    return mPackageName;
  }

  @NotNull
  public String getFullyQualifiedName() {
    return mPackageName + '.' + mClassName;
  }

  /**
   * A Builder for the TableClass class.
   */
  public static class Builder {

    @Nullable
    private String mTableName;

    @Nullable
    private String mClassName;

    @Nullable
    private String mPackageName;

    @Nullable
    private Element mElement;

    public TableClass build() {
      if (mTableName == null) {
        throw new IllegalStateException("TableClass needs a table name.");
      }

      if (mClassName == null) {
        throw new IllegalStateException("TableClass needs a class name.");
      }

      if (mPackageName == null) {
        throw new IllegalStateException("TableClass needs a package name");
      }

      if (mElement == null) {
        throw new IllegalStateException("TableClass needs an Element");
      }

      return new TableClass(mTableName, mClassName, mPackageName, mElement);
    }

    public Builder withTableName(@NotNull final String tableName) {
      mTableName = tableName;
      return this;
    }

    public Builder withClassName(@NotNull final String className) {
      mClassName = className;
      return this;
    }

    public Builder withPackageName(@NotNull final String packageName) {
      mPackageName = packageName;
      return this;
    }

    public Builder withElement(@NotNull final Element element) {
      mElement = element;
      return this;
    }
  }
}

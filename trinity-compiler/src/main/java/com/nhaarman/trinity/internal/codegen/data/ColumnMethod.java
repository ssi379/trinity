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
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A class which holds information about a getter or setter method for a column.
 */
public class ColumnMethod {

  /**
   * The name of the method.
   */
  @NotNull
  private final String mName;

  /**
   * The type of the column.
   */
  @NotNull
  private final TypeMirror mType;

  /**
   * Whether this method is a getter.
   */
  private final boolean mIsGetter;

  /**
   * Whether this column is a primary column.
   */
  private final boolean mIsPrimary;

  @NotNull
  private final Element mElement;

  private ColumnMethod(@NotNull final String name,
                       @NotNull final TypeMirror type,
                       final boolean isGetter,
                       final boolean isPrimary,
                       @NotNull final Element element) {
    mName = name;
    mType = type;
    mElement = element;
    mIsPrimary = isPrimary;
    mIsGetter = isGetter;
  }

  /**
   * Returns the name of the method.
   */
  @NotNull
  public String getName() {
    return mName;
  }

  /**
   * Returns the type of the column.
   */
  @NotNull
  public TypeMirror getType() {
    return mType;
  }

  /**
   * Returns true if the method is a getter.
   */
  public boolean isGetter() {
    return mIsGetter;
  }

  /**
   * Returns true if the method is a setter.
   */
  public boolean isSetter() {
    return !mIsGetter;
  }

  /**
   * Returns true if the column is a primary column.
   */
  public boolean isPrimary() {
    return mIsPrimary;
  }

  /**
   * Returns the Element belonging to this method.
   */
  @NotNull
  public Element getElement() {
    return mElement;
  }

  public static class Builder {

    @Nullable
    private String mName;

    @Nullable
    private TypeMirror mType;

    private boolean mIsGetter;

    @Nullable
    private Element mElement;

    private boolean mIsPrimary;

    @NotNull
    public ColumnMethod build() {
      if (mName == null) {
        throw new IllegalStateException("ColumnMethod needs a name.");
      }

      if (mType == null) {
        throw new IllegalStateException("ColumnMethod needs a type");
      }

      if (mElement == null) {
        throw new IllegalStateException("ColumnMethod needs an Element.");
      }

      return new ColumnMethod(mName, mType, mIsGetter, mIsPrimary, mElement);
    }

    public Builder withName(@NotNull final String name) {
      mName = name;
      return this;
    }

    public Builder withType(@NotNull final TypeMirror type) {
      mType = type;
      return this;
    }

    public Builder isGetter() {
      mIsGetter = true;
      return this;
    }

    public Builder isSetter() {
      mIsGetter = false;
      return this;
    }

    public Builder isPrimary() {
      mIsPrimary = true;
      return this;
    }

    public Builder withElement(@NotNull final Element executableElement) {
      mElement = executableElement;
      return this;
    }
  }
}

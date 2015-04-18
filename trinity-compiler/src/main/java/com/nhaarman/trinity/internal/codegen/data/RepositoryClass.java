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

import com.nhaarman.trinity.internal.codegen.method.RepositoryMethod;
import java.util.Collection;
import java.util.Collections;
import javax.lang.model.element.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RepositoryClass {

  @NotNull
  private final String mClassName;

  @NotNull
  private final String mPackageName;

  @NotNull
  private final String mTableClassName;

  @NotNull
  private final String mTableClassPackageName;

  private final boolean mIsInterface;

  @NotNull
  private final Element mElement;

  @NotNull
  private final Collection<RepositoryMethod> mMethods;

  private RepositoryClass(@NotNull final String className,
                          @NotNull final String packageName,
                          @NotNull final String tableClassName,
                          @NotNull final String tableClassPackageName,
                          final boolean isInterface,
                          @NotNull final Collection<RepositoryMethod> methods,
                          @NotNull final Element element) {
    mClassName = className;
    mPackageName = packageName;
    mTableClassName = tableClassName;
    mTableClassPackageName = tableClassPackageName;
    mIsInterface = isInterface;
    mElement = element;
    mMethods = methods;
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

  @NotNull
  public String getTableClassName() {
    return mTableClassName;
  }

  @NotNull
  public String getTableClassPackageName() {
    return mTableClassPackageName;
  }

  @NotNull
  public String getTableClassFullyQualifiedName() {
    return mTableClassPackageName + '.' + mTableClassName;
  }

  public boolean isInterface() {
    return mIsInterface;
  }

  @NotNull
  public Collection<RepositoryMethod> getMethods() {
    return Collections.unmodifiableCollection(mMethods);
  }

  @NotNull
  public Element getElement() {
    return mElement;
  }

  public static class Builder {

    @Nullable
    private String mClassName;

    @Nullable
    private String mPackageName;

    @Nullable
    private String mTableClassName;

    @Nullable
    private String mTableClassPackageName;

    private boolean mIsInterface;

    @Nullable
    private Collection<RepositoryMethod> mMethods;

    @Nullable
    private Element mElement;

    public RepositoryClass build() {
      if (mClassName == null) {
        throw new IllegalStateException("RepositoryClass needs a class name.");
      }

      if (mPackageName == null) {
        throw new IllegalStateException("RepositoryClass needs a package name.");
      }

      if (mTableClassName == null) {
        throw new IllegalStateException("RepositoryClass needs a table class name.");
      }

      if (mTableClassPackageName == null) {
        throw new IllegalStateException("RepositoryClass needs a table class package name.");
      }

      if (mMethods == null) {
        throw new IllegalStateException("RepositoryClass needs methods");
      }

      if (mElement == null) {
        throw new IllegalStateException("RepositoryClass needs an Element");
      }

      return new RepositoryClass(mClassName, mPackageName, mTableClassName, mTableClassPackageName, mIsInterface, mMethods, mElement);
    }

    public Builder withClassName(@NotNull final String className) {
      mClassName = className;
      return this;
    }

    public Builder withPackageName(@NotNull final String packageName) {
      mPackageName = packageName;
      return this;
    }

    public Builder withTableClassName(@NotNull final String tableClassName) {
      mTableClassName = tableClassName;
      return this;
    }

    public Builder withTableClassPackageName(@NotNull final String tableClassPackageName) {
      mTableClassPackageName = tableClassPackageName;
      return this;
    }

    public Builder isInterface() {
      mIsInterface = true;
      return this;
    }

    public Builder withMethods(@NotNull final Collection<RepositoryMethod> methods) {
      mMethods = methods;
      return this;
    }

    public Builder withElement(@NotNull final Element element) {
      mElement = element;
      return this;
    }
  }
}

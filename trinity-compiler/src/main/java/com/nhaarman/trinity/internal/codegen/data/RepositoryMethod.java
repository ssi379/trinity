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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RepositoryMethod {

  @NotNull
  private final String mName;

  @NotNull
  private final TypeMirror mType;

  @NotNull
  private final Collection<Parameter> mParameters;

  @NotNull
  private final Element mElement;

  public RepositoryMethod(@NotNull final String name,
                          @NotNull final TypeMirror type,
                          @NotNull final Collection<Parameter> parameters,
                          @NotNull final Element element) {
    mName = name;
    mType = type;
    mParameters = parameters;
    mElement = element;
  }

  @NotNull
  public String getMethodName() {
    return mName;
  }

  @NotNull
  public TypeMirror getReturnType() {
    return mType;
  }

  public Collection<Parameter> getParameters() {
    return Collections.unmodifiableCollection(mParameters);
  }

  public Parameter getParameter() {
    return mParameters.iterator().next();
  }

  @NotNull
  public Element getElement() {
    return mElement;
  }

  public static class Builder {

    @Nullable
    private String mName;

    @Nullable
    private TypeMirror mType;

    @Nullable
    private Collection<Parameter> mParameters;

    @Nullable
    private Element mElement;

    @NotNull
    public RepositoryMethod build() {
      if (mName == null) {
        throw new IllegalStateException("RepositoryMethod needs a name.");
      }

      if (mType == null) {
        throw new IllegalStateException("RepositoryMethod needs a type");
      }

      if (mParameters == null) {
        throw new IllegalStateException("RepositoryMethod needs parameters");
      }

      if (mElement == null) {
        throw new IllegalStateException("RepositoryMethod needs an Element");
      }

      return new RepositoryMethod(mName, mType, mParameters, mElement);
    }

    @NotNull
    public Builder withName(@NotNull final String name) {
      mName = name;
      return this;
    }

    @NotNull
    public Builder withType(@NotNull final TypeMirror type) {
      mType = type;
      return this;
    }

    @NotNull
    public Builder withParameters(@NotNull final Collection<Parameter> parameters) {
      mParameters = parameters;
      return this;
    }

    @NotNull
    public Builder withElement(@NotNull final Element element) {
      mElement = element;
      return this;
    }
  }
}

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

package com.nhaarman.trinity.internal.codegen.method;

import com.nhaarman.trinity.internal.codegen.data.Parameter;
import java.util.Collection;
import java.util.Collections;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

public abstract class RepositoryMethod implements Comparable<RepositoryMethod> {

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
  public String getReturnType() {
    return mType.toString();
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

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    for (Modifier modifier : mElement.getModifiers()) {
      stringBuilder.append(modifier).append(' ');
    }

    stringBuilder.append(((ExecutableElement) mElement).getReturnType()).append(' ');
    stringBuilder.append(mElement);

    return stringBuilder.toString();
  }

  public abstract void accept(@NotNull final MethodVisitor visitor);

  @Override
  public int compareTo(final RepositoryMethod o) {
    return mName.compareTo(o.mName);
  }
}

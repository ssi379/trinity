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

public class RepositoryMethod {

  private final ExecutableElement mExecutableElement;

  private final Collection<Parameter> mParameters = new LinkedHashSet<>();

  public RepositoryMethod(final ExecutableElement executableElement) {
    mExecutableElement = executableElement;

    for (VariableElement variableElement : mExecutableElement.getParameters()) {
      mParameters.add(new Parameter(variableElement));
    }
  }

  public String getMethodName() {
    return mExecutableElement.getSimpleName().toString();
  }

  public String getReturnType() {
    return mExecutableElement.getReturnType().toString();
  }

  public Collection<Parameter> getParameters() {
    return Collections.unmodifiableCollection(mParameters);
  }

  public Parameter getParameter() {
    return mParameters.iterator().next();
  }

  public Element getElement() {
    return mExecutableElement;
  }

  public static class Parameter {

    private final String mType;

    private final String mName;

    public Parameter(final VariableElement variableElement) {
      mType = variableElement.asType().toString();
      mName = variableElement.getSimpleName().toString();
    }

    public String getType() {
      return mType;
    }

    public String getName() {
      return mName;
    }
  }
}

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

package com.nhaarman.trinity.query.create;

public enum OnAction {

  SET_NULL("SET NULL"),
  SET_DEFAULT("SET DEFAULT"),
  CASCADE("CASCADE"),
  RESTRICT("RESTRICT"),
  NO_ACTION("NO ACTION");

  private final String mText;

  OnAction(final String text) {
    mText = text;
  }

  @Override
  public String toString() {
    return mText;
  }
}

/*
 * Copyright (C) 2006 The Android Open Source Project
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
package com.nhaarman.trinity.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TextUtils {

  /**
   * Returns a string containing the tokens joined by delimiters.
   *
   * @param tokens an array objects to be joined. Strings will be formed from
   * the objects by calling object.toString().
   */
  public static String join(@NotNull final CharSequence delimiter, @NotNull final Object[] tokens) {
    StringBuilder sb = new StringBuilder(256);
    boolean firstTime = true;
    for (Object token : tokens) {
      if (firstTime) {
        firstTime = false;
      } else {
        sb.append(delimiter);
      }
      sb.append(token);
    }
    return sb.toString();
  }

  public static String[] toStringArray(@Nullable final Object[] array) {
    if (array == null) {
      return null;
    }
    final String[] transformedArray = new String[array.length];
    for (int i = 0; i < array.length; i++) {
      transformedArray[i] = String.valueOf(array[i]);
    }
    return transformedArray;
  }

  public static String[] join(@Nullable final String[] array1, @Nullable final String... array2) {
    if (array1 == null) {
      return clone(array2);
    }
    if (array2 == null) {
      return clone(array1);
    }

    final String[] joinedArray = new String[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  private static String[] clone(@Nullable final String[] array) {
    if (array == null) {
      return null;
    }
    return array.clone();
  }
}

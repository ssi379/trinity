/*
 * Copyright (C) 2014 Michael Pardo
 * Copyright (C) 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;

public abstract class ExecutableQueryBase extends QueryBase implements ExecutableQuery {

  protected ExecutableQueryBase(final Query parent, final String table) {
    super(parent, table);
  }

  @Override
  public void executeOn(final SQLiteDatabase database) {
    database.execSQL(getSql(), getArgs());
  }
}

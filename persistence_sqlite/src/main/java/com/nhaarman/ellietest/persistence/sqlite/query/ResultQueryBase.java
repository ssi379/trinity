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

package com.nhaarman.ellietest.persistence.sqlite.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


@SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
public abstract class ResultQueryBase extends ExecutableQueryBase implements ResultQuery {

    protected ResultQueryBase(final Query parent, final String table) {
        super(parent, table);
    }

    @Override
    public Cursor fetchFrom(final SQLiteDatabase database) {
        System.out.println(getSql());
        return database.rawQuery(getSql(), getArgs());
    }
}
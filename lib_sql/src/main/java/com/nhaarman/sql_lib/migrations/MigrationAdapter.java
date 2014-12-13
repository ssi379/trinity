/*
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

package com.nhaarman.sql_lib.migrations;

public class MigrationAdapter implements Migration {

    private final int mVersion;

    public MigrationAdapter(final int version) {
        mVersion = version;
    }

    @Override
    public int getVersion() {
        return mVersion;
    }

    @Override
    public long getOrder() {
        return 0;
    }

    @Override
    public void beforeUp() {
    }

    /**
     * Returns the SQL statements which are to be executed in order to perform this migration.
     *
     * @return The SQL statements.
     */
    @Override
    public String[] getUpStatements() {
        return new String[0];
    }

    @Override
    public void afterUp() {
    }

    @Override
    public void beforeDown() {
    }

    @Override
    public String[] getDownStatements() {
        return new String[0];
    }

    @Override
    public void afterDown() {
    }

    @Override
    public int compareTo(final Migration another) {
        return Long.valueOf(getOrder()).compareTo(another.getOrder());
    }
}
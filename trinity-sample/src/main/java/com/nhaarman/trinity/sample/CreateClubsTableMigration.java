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

package com.nhaarman.trinity.sample;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.sqlitebuilder.android.AndroidStatementExecutor;
import com.nhaarman.trinity.migrations.MigrationAdapter;

import static com.nhaarman.sqlitebuilder.impl.Statements.column;
import static com.nhaarman.sqlitebuilder.impl.Statements.create;

public class CreateClubsTableMigration extends MigrationAdapter {

  static final int VERSION = 1;

  public CreateClubsTableMigration() {
    super(VERSION);
  }

  @Override
  public void onUpgrade(final SQLiteDatabase database) {
    create()
        .table("clubs")
        .columns(
            column("id"),
            column("name")
        )
        .executeOn(new AndroidStatementExecutor(database));
  }
}

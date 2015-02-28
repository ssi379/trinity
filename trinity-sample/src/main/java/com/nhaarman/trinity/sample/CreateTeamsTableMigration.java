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
import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;

import static com.nhaarman.trinity.query.create.Column.integer;
import static com.nhaarman.trinity.query.create.Column.text;
import static com.nhaarman.trinity.query.create.Create.create;

@Migration(version = 2, order = CreateTeamsTableMigration.VERSION)
public class CreateTeamsTableMigration extends MigrationAdapter {

  static final int VERSION = 2;

  public CreateTeamsTableMigration() {
    super(VERSION);
  }

  @Override
  public void onUpgrade(final SQLiteDatabase database) {
    create()
        .table("teams")
        .columns(
            integer("id").primaryKey(),
            text("name"),
            integer("club_id").references("clubs").columns("id")
        )
        .executeOn(database);
  }
}

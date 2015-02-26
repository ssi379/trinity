package com.nhaarman.trinity.example;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;
import com.nhaarman.trinity.query.create.Create;

import static com.nhaarman.trinity.query.create.Column.integer;
import static com.nhaarman.trinity.query.create.Column.text;

@Migration(version = 2, order = CreateTeamsTableMigration.VERSION)
public class CreateTeamsTableMigration extends MigrationAdapter {

  static final int VERSION = 2;

  public CreateTeamsTableMigration() {
    super(VERSION);
  }

  @Override
  public void onUpgrade(final SQLiteDatabase database) {
    Create.create()
        .table("teams")
        .columns(
            integer("id").primaryKey(),
            text("name"),
            integer("club_id").references("clubs").columns("id")
        )
        .executeOn(database);
  }
}

package com.nhaarman.trinity.example;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;

import static com.nhaarman.trinity.query.create.Column.integer;
import static com.nhaarman.trinity.query.create.Column.text;
import static com.nhaarman.trinity.query.create.Create.create;

@Migration(version = 3, order = CreatePlayersTableMigration.VERSION)
public class CreatePlayersTableMigration extends MigrationAdapter {

  static final int VERSION = 3;

  public CreatePlayersTableMigration() {
    super(VERSION);
  }

  @Override
  public void onUpgrade(final SQLiteDatabase database) {
    create().table("players")
        .columns(
            integer("id").primaryKey(),
            text("name"),
            integer("team_id").references("teams").columns("id")
        )
        .executeOn(database);
  }
}
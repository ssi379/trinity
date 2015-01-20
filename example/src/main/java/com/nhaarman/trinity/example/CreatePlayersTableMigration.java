package com.nhaarman.trinity.example;

import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;

@Migration(version = 3, order = CreatePlayersTableMigration.VERSION)
public class CreatePlayersTableMigration extends MigrationAdapter {

  static final int VERSION = 3;

  public CreatePlayersTableMigration() {
    super(VERSION);
  }

  @Override
  public String[] getUpStatements() {
    return new String[]{
        "CREATE TABLE players (" +
            "id INTEGER PRIMARY KEY NOT NULL," +
            "name STRING," +
            "team_id INTEGER REFERENCES teams(id)" +
            ")"
    };
  }
}

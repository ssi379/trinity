package com.nhaarman.trinity.example;

import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;

@Migration(version = 2, order = CreateTeamsTableMigration.VERSION)
public class CreateTeamsTableMigration extends MigrationAdapter {

  static final int VERSION = 2;

  public CreateTeamsTableMigration() {
    super(VERSION);
  }

  @Override
  public String[] getUpStatements() {
    return new String[]{
        "CREATE TABLE teams (" +
            "id INTEGER PRIMARY KEY NOT NULL," +
            "name STRING," +
            "club_id INTEGER REFERENCES clubs(id)" +
            ")"
    };
  }
}

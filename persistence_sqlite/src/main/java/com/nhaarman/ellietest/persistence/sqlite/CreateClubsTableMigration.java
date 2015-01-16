package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.lib_setup.annotations.Migration;
import com.nhaarman.lib_setup.migrations.MigrationAdapter;

@Migration(version = 1, order = CreateClubsTableMigration.VERSION)
public class CreateClubsTableMigration extends MigrationAdapter {

  static final int VERSION = 1;

  public CreateClubsTableMigration() {
    super(VERSION);
  }

  @Override
  public String[] getUpStatements() {
    return new String[]{
        "CREATE TABLE clubs (" +
            "id INTEGER PRIMARY KEY NOT NULL," +
            "name STRING" +
            ")"
    };
  }
}

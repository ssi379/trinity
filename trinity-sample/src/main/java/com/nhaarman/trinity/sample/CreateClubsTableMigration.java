package com.nhaarman.trinity.sample;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;

import static com.nhaarman.trinity.query.create.Column.integer;
import static com.nhaarman.trinity.query.create.Column.text;
import static com.nhaarman.trinity.query.create.Create.create;

@Migration(version = 1, order = CreateClubsTableMigration.VERSION)
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
            integer("id").primaryKey(),
            text("name")
        )
        .executeOn(database);
  }
}

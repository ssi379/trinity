package com.nhaarman.trinity.example;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;

import static com.nhaarman.trinity.query.Create.Column.Type.INTEGER;
import static com.nhaarman.trinity.query.Create.Column.Type.TEXT;
import static com.nhaarman.trinity.query.Create.create;

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
        .withColumn("id").withType(INTEGER).withPrimaryKey()
        .and().withColumn("name").withType(TEXT)
        .executeOn(database);
  }
}

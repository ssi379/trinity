package com.nhaarman.trinity.example;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;

import static com.nhaarman.trinity.query.Create.Column.Type.INTEGER;
import static com.nhaarman.trinity.query.Create.Column.Type.TEXT;
import static com.nhaarman.trinity.query.Create.create;

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
        .withColumn("id").type(INTEGER).withPrimaryKey()
        .and().withColumn("name").withType(TEXT)
        .and().withColumn("club_id").withType(INTEGER)
        .executeOn(database);
  }
}

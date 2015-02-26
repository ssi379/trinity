package com.nhaarman.trinity.example;

import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.annotations.Migration;
import com.nhaarman.trinity.migrations.MigrationAdapter;

import static com.nhaarman.trinity.query.Create.Column.Type.INTEGER;
import static com.nhaarman.trinity.query.Create.Column.Type.TEXT;
import static com.nhaarman.trinity.query.Create.create;

@Migration(version = 3, order = CreatePlayersTableMigration.VERSION)
public class CreatePlayersTableMigration extends MigrationAdapter {

  static final int VERSION = 3;

  public CreatePlayersTableMigration() {
    super(VERSION);
  }

  @Override
  public void onUpgrade(final SQLiteDatabase database) {
    create().table("players")
        .withColumn("id").type(INTEGER).withPrimaryKey()
        .and().withColumn("name").withType(TEXT)
        .and().withColumn("team_id").withType(INTEGER)
        .executeOn(database);
  }
}
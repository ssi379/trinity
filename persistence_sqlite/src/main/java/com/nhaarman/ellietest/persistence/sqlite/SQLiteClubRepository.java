package com.nhaarman.ellietest.persistence.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nhaarman.ellietest.core.Club;
import com.nhaarman.ellietest.core.ClubRepository;
import com.nhaarman.lib_setup.annotations.Repository;
import com.nhaarman.sql_lib.query.Select;

import java.util.ArrayList;
import java.util.List;

//@Repository(tableName = "clubs")
public class SQLiteClubRepository implements ClubRepository {

  private final SQLiteDatabase mDatabase;

  SQLiteClubRepository(final SQLiteDatabase database) {
    mDatabase = database;
  }

//  @Override
//  public Long create(final Club object) {
//    ContentValues contentValues = createContentValues(object);
//    long id = mDatabase.insert("clubs", null, contentValues);
//    if (id != -1) {
//      object.setId(id);
//    }
//    return id;
//  }
//
//  @Override
//  public boolean update(final Club object) {
//    ContentValues contentValues = createContentValues(object);
//    return mDatabase.update(
//        "clubs",
//        contentValues,
//        "id",
//        new String[]{String.valueOf(object.getId())}
//    ) == 1;
//  }

  @Override
  public Club find(final Long id) {
    Club result = null;

    Cursor cursor = new Select().from("players").where("id=?", id).fetchFrom(mDatabase);
    if (cursor.moveToFirst()) {
      result = read(cursor);
    }

    return result;
  }

  public final ContentValues createContentValues(final Club entity) {
    ContentValues values = new ContentValues();
    values.put("id", entity.getId());
    values.put("name", entity.getName());
    return values;
  }

  private List<Club> readClubs(final Cursor cursor) {
    List<Club> result = new ArrayList<>();

    if (cursor.moveToFirst()) {
      do {
        result.add(read(cursor));
      }
      while (cursor.moveToNext());
    }

    return result;
  }

  private Club read(final Cursor cursor) {
    Club player = new Club();

    player.setId(cursor.getLong(cursor.getColumnIndex("id")));
    player.setName(cursor.getString(cursor.getColumnIndex("name")));

    return player;
  }
}

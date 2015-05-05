package trinityprocessortest.full;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.lang.Long;
import java.lang.Override;
import java.util.ArrayList;
import java.util.List;

public final class TrinityMyRepository implements MyRepository {
  /**
   * The {@link SQLiteDatabase} that is used for persistence.
   */
  private final SQLiteDatabase mDatabase;

  public TrinityMyRepository(final SQLiteDatabase database) {
    mDatabase = database;
  }

  public ContentValues createContentValues(final MyEntity entity) {
    ContentValues result = new ContentValues();

    result.put("name", entity.getName());
    result.put("id", entity.getId());

    return result;
  }

  public MyEntity read(final Cursor cursor) {
    MyEntity result = new MyEntity();

    result.setId(cursor.getLong(cursor.getColumnIndex("id")));
    result.setName(cursor.getString(cursor.getColumnIndex("name")));

    return result;
  }

  /**
   * Executes an insert statement to persist given trinityprocessortest.full.MyEntity in the database.
   * When successful, the id of the trinityprocessortest.full.MyEntity will be set to the id of the created row.
   *
   * @param entity The trinityprocessortest.full.MyEntity to insert.
   *
   * @return The created row id, or null if an error occurred.
   */
  @Override
  public Long create(final MyEntity entity) {
    Long result = null;

    ContentValues contentValues = createContentValues(entity);
    long id = mDatabase.insert("entities", null, contentValues);
    if (id != -1) {
      result = id;
    }

    return result;
  }

  /**
   * Performs a query to find all instances of trinityprocessortest.full.MyEntity.
   *
   * @return A list which contains all instances of trinityprocessortest.full.MyEntity.
   */
  @Override
  public List findAll() {
    List results = new ArrayList<MyEntity>();

    Cursor cursor = mDatabase.query("entities", null, null, null, null, null, null);
    try {
      while (cursor.moveToNext()) {
        results.add(read(cursor));
      }
    } finally{
      cursor.close();
    }

    return results;
  }

  /**
   * Performs a query for a trinityprocessortest.full.MyEntity with given id.
   * If no such instance is found, null is returned.
   *
   * @param id The id of the instance to find.
   * @return The trinityprocessortest.full.MyEntity with given id, or null if it doesn't exist. */
  @Override
  public MyEntity findById(final Long id) {
    if (id == null) {
      return null;
    }

    MyEntity result = null;

    Cursor cursor = mDatabase.query("entities", null, "id=?", new String[] {String.valueOf(id)}, null, null, null, "1");
    try {
      if (cursor.moveToFirst()) {
        result = read(cursor);
      }
    } finally{
      cursor.close();
    }

    return result;
  }
}
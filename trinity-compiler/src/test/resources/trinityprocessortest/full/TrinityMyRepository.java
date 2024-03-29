package trinityprocessortest.full;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.lang.Long;
import java.lang.Override;
import java.util.ArrayList;
import java.util.Collection;
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

    result.put("some_boxed_boolean", entity.getSomeBoxedBoolean());
    result.put("some_boolean", entity.getSomeBoolean());
    result.put("name", entity.getName());
    result.put("some_int", entity.getSomeInt());
    result.put("some_integer", entity.getSomeInteger());
    result.put("id", entity.getId());
    result.put("my_object", new MyObjectTypeSerializer().serialize(entity.getMyObject()));

    return result;
  }

  public MyEntity read(final Cursor cursor) {
    MyEntity result = new MyEntity();

    result.setSomeBoolean(cursor.getInt(cursor.getColumnIndex("some_boolean")) == 1);
    result.setMyObject(new MyObjectTypeSerializer().deserialize(cursor.getString(cursor.getColumnIndex("my_object"))));
    result.setName(cursor.getString(cursor.getColumnIndex("name")));
    result.setSomeInt(cursor.getInt(cursor.getColumnIndex("some_int")));
    result.setSomeInteger(cursor.getInt(cursor.getColumnIndex("some_integer")));
    result.setSomeBoxedBoolean(cursor.getInt(cursor.getColumnIndex("some_boxed_boolean")) == 1);
    result.setId(cursor.getLong(cursor.getColumnIndex("id")));

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
   * Executes insert statements to persist given java.util.Collection<trinityprocessortest.full.MyEntity> in the database.
   *
   * @param entities The entities to insert.
   */
  @Override
  public void createAll(final Collection<MyEntity> entities) {
    try {
      mDatabase.beginTransaction();

      for (MyEntity entity: entities) {
        ContentValues contentValues = createContentValues(entity);
        mDatabase.insert("entities", null, contentValues);
      }

      mDatabase.setTransactionSuccessful();
    } finally{
      mDatabase.endTransaction();
    }
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
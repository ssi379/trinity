package trinityprocessortest.empty;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    return result;
  }

  public MyEntity read(final Cursor cursor) {
    MyEntity result = new MyEntity();


    return result;
  }
}
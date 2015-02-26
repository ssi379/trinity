package com.nhaarman.trinity.query.insert;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.nhaarman.trinity.query.ExecutableQuerySqlPart;
import com.nhaarman.trinity.query.SqlPart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ExecutableInsertQuerySqlPart extends ExecutableQuerySqlPart {

  ExecutableInsertQuerySqlPart(@Nullable final SqlPart previousSqlPart) {
    super(previousSqlPart);
  }

  public int execute(@NotNull final SQLiteDatabase database) {
    Cursor cursor = queryOn(database);
    try {
      if (cursor.moveToFirst()) {
        return cursor.getInt(0);
      }
    } finally {
      cursor.close();
    }

    return -1;
  }
}

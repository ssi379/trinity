package com.nhaarman.trinity.query;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;

public interface ExecutableQuery extends Executable {

  @NotNull
  Cursor queryOn(@NotNull SQLiteDatabase database);
}

package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import org.jetbrains.annotations.NotNull;

public interface ExecutableStatement extends Executable {

  void executeOn(@NotNull SQLiteDatabase database);
}

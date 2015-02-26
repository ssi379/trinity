package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.SqlStart;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("HardCodedStringLiteral")
public class Create extends SqlStart {

  public Table table(@NotNull @NonNls final String tableName) {
    return new Table(this, tableName);
  }

  public TemporaryTable temporaryTable(@NotNull @NonNls final String tableName) {
    return new TemporaryTable(this, tableName);
  }

  @NotNull
  @Override
  protected String getPartSql() {
    return "CREATE";
  }

  public static Create create() {
    return new Create();
  }
}

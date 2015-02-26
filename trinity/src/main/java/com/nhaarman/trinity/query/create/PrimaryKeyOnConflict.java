package com.nhaarman.trinity.query.create;

public interface PrimaryKeyOnConflict extends ColumnInfo {

  AutoIncrement autoIncrement();
}

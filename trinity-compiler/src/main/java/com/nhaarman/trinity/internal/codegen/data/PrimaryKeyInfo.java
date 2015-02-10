package com.nhaarman.trinity.internal.codegen.data;

public class PrimaryKeyInfo {

  private final boolean mAutoIncrement;

  public PrimaryKeyInfo(final boolean autoIncrement) {
    mAutoIncrement = autoIncrement;
  }

  public boolean autoIncrement() {
    return mAutoIncrement;
  }
}
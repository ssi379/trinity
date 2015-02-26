package com.nhaarman.trinity.query.create;

public enum OnAction {

  SET_NULL("SET NULL"),
  SET_DEFAULT("SET DEFAULT"),
  CASCADE("CASCADE"),
  RESTRICT("RESTRICT"),
  NO_ACTION("NO ACTION");

  private final String mText;

  OnAction(final String text) {
    mText = text;
  }

  @Override
  public String toString() {
    return mText;
  }
}

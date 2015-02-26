package com.nhaarman.trinity.query.create;

import org.junit.Test;

import static com.nhaarman.trinity.query.create.ConflictAction.FAIL;
import static com.nhaarman.trinity.query.create.ConflictAction.ROLLBACK;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

@SuppressWarnings("HardCodedStringLiteral")
public class PrimaryKeyTest {

  @Test
  public void primaryKey_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new PrimaryKey(null);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("PRIMARY KEY"));
  }

  @Test
  public void ascendingPrimaryKey_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new PrimaryKey(null).ascending();

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("PRIMARY KEY ASC"));
  }

  @Test
  public void descendingPrimaryKey_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new PrimaryKey(null).descending();

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("PRIMARY KEY DESC"));
  }

  @Test
  public void ascendingAutoIncrementPrimaryKey_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new PrimaryKey(null).ascending().autoIncrement();

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("PRIMARY KEY ASC AUTOINCREMENT"));
  }

  @Test
  public void descendingAutoIncrementPrimaryKey_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new PrimaryKey(null).descending().autoIncrement();

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("PRIMARY KEY DESC AUTOINCREMENT"));
  }

  @Test
  public void onConflictFailPrimaryKey_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new PrimaryKey(null).onConflict(FAIL);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("PRIMARY KEY ON CONFLICT FAIL"));
  }

  @Test
  public void descendingAutoIncrementOnConflictRollbackPrimaryKey_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new PrimaryKey(null).descending().onConflict(ROLLBACK).autoIncrement();

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("PRIMARY KEY DESC ON CONFLICT ROLLBACK AUTOINCREMENT"));
  }
}
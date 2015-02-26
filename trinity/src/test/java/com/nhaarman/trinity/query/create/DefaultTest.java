package com.nhaarman.trinity.query.create;

import org.junit.Test;

import static com.nhaarman.trinity.query.create.Default.CURRENT_DATE;
import static com.nhaarman.trinity.query.create.Default.CURRENT_TIME;
import static com.nhaarman.trinity.query.create.Default.CURRENT_TIMESTAMP;
import static com.nhaarman.trinity.query.create.Default.NULL;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

@SuppressWarnings("HardCodedStringLiteral")
public class DefaultTest {

  @Test
  public void defaultSignedNumber_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Default(null, 5);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("DEFAULT 5"));
  }

  @Test
  public void defaultSignedNumberDouble_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Default(null, 5.0);

    /* When */
    String sql = constraint.getSql();


    /* Then */
    assertThat(sql, is("DEFAULT 5.0"));
  }

  @Test
  public void defaultLiteral_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Default(null, "Hello");

    /* When */
    String sql = constraint.getSql();


    /* Then */
    assertThat(sql, is("DEFAULT Hello"));
  }

  @Test
  public void defaultNullValue_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Default(null, NULL);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("DEFAULT NULL"));
  }

  @Test
  public void defaultCurrentTime_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Default(null, CURRENT_TIME);

    /* When */
    String sql = constraint.getSql();


    /* Then */
    assertThat(sql, is("DEFAULT CURRENT_TIME"));
  }

  @Test
  public void defaultCurrentDate_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Default(null, CURRENT_DATE);

    /* When */
    String sql = constraint.getSql();


    /* Then */
    assertThat(sql, is("DEFAULT CURRENT_DATE"));
  }

  @Test
  public void defaultCurrentTimestamp_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Default(null, CURRENT_TIMESTAMP);

    /* When */
    String sql = constraint.getSql();


    /* Then */
    assertThat(sql, is("DEFAULT CURRENT_TIMESTAMP"));
  }
}
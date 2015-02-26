package com.nhaarman.trinity.query.create;

import org.junit.Test;

import static com.nhaarman.trinity.query.create.Column.integer;
import static com.nhaarman.trinity.query.create.Column.none;
import static com.nhaarman.trinity.query.create.Column.numeric;
import static com.nhaarman.trinity.query.create.Column.real;
import static com.nhaarman.trinity.query.create.Column.text;
import static com.nhaarman.trinity.query.create.OnAction.CASCADE;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

@SuppressWarnings("HardCodedStringLiteral")
public class ColumnTest {

  @Test
  public void integerColumn_returnsProperSql() {
    /* Given */
    Column column = integer("MyColumn");
    
    /* When */
    String sql = column.getSql();

    /* Then */
    assertThat(sql, is("MyColumn INTEGER"));
  }

  @Test
  public void textColumn_returnsProperSql() {
    /* Given */
    Column column = text("MyColumn");

    /* When */
    String sql = column.getSql();

    /* Then */
    assertThat(sql, is("MyColumn TEXT"));
  }

  @Test
  public void noneColumn_returnsProperSql() {
    /* Given */
    Column column = none("MyColumn");

    /* When */
    String sql = column.getSql();

    /* Then */
    assertThat(sql, is("MyColumn NONE"));
  }

  @Test
  public void realColumn_returnsProperSql() {
    /* Given */
    Column column = real("MyColumn");

    /* When */
    String sql = column.getSql();

    /* Then */
    assertThat(sql, is("MyColumn REAL"));
  }

  @Test
  public void numericColumn_returnsProperSql() {
    /* Given */
    Column column = numeric("MyColumn");

    /* When */
    String sql = column.getSql();

    /* Then */
    assertThat(sql, is("MyColumn NUMERIC"));
  }

  @Test
  public void columnWithSingleConstraint_returnsProperSql() {
     /* Given */
    ColumnInfo queryPart =
        numeric("MyColumn").references("MyTable").columns("MyForeignColumn").onDelete(CASCADE);

    /* When */
    String sql = queryPart.getSql();

    /* Then */
    assertThat(sql, is("MyColumn NUMERIC REFERENCES MyTable (MyForeignColumn) ON DELETE CASCADE"));
  }

  @Test
  public void columnWithMultipleConstraints_returnsProperSql() {
     /* Given */
    ColumnInfo queryPart = numeric("MyColumn")
        .references("MyTable").columns("MyForeignColumn").onDelete(CASCADE)
        .notNull();

    /* When */
    String sql = queryPart.getSql();

    /* Then */
    assertThat(sql, is("MyColumn NUMERIC REFERENCES MyTable (MyForeignColumn) ON DELETE CASCADE NOT NULL"));
  }
}
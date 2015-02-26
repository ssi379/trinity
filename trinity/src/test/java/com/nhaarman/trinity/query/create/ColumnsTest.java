package com.nhaarman.trinity.query.create;

import org.junit.Test;

import static com.nhaarman.trinity.query.create.Column.integer;
import static com.nhaarman.trinity.query.create.Column.numeric;
import static com.nhaarman.trinity.query.create.Column.text;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

@SuppressWarnings("HardCodedStringLiteral")
public class ColumnsTest {

  @Test
  public void singleColumn_returnsProperPartSql() {
    /* Given */
    Columns columns = new Columns(null, integer("MyColumn"));

    /* When */
    String partSql = columns.getPartSql();

    /* Then */
    assertThat(partSql, is("(MyColumn INTEGER)"));
  }

  @Test
  public void multipleColumns_returnsProperPartSql() {
    /* Given */
    Columns columns = new Columns(null, integer("MyColumn1"), text("MyColumn2"), numeric("MyColumn3"));

    /* When */
    String partSql = columns.getPartSql();

    /* Then */
    assertThat(partSql, is("(MyColumn1 INTEGER,MyColumn2 TEXT,MyColumn3 NUMERIC)"));
  }
}
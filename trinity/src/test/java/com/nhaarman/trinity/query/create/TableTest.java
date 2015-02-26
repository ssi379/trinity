package com.nhaarman.trinity.query.create;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class TableTest {

  @Test
  public void table_returnsProperPartSql() {
    /* Given */
    Table myTable = new Table(null, "MyTable");

    /* When */
    String partSql = myTable.getPartSql();

    /* Then */
    assertThat(partSql, is("TABLE MyTable"));
  }
}
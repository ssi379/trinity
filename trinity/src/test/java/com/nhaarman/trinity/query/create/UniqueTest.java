package com.nhaarman.trinity.query.create;

import org.junit.Test;

import static com.nhaarman.trinity.query.create.ConflictAction.IGNORE;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class UniqueTest {

  @Test
  public void unique_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Unique(null);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("UNIQUE"));
  }

  @Test
  public void uniqueOnConflictAbort_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new Unique(null).onConflict(IGNORE);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("UNIQUE ON CONFLICT IGNORE"));
  }
}
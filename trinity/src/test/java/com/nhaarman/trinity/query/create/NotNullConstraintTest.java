package com.nhaarman.trinity.query.create;

import org.junit.Test;

import static com.nhaarman.trinity.query.create.ConflictAction.ABORT;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class NotNullConstraintTest {

  @Test
  public void notNull_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new NotNullConstraint(null);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("NOT NULL"));
  }

  @Test
  public void notNullOnConflictAbort_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new NotNullConstraint(null).onConflict(ABORT);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("NOT NULL ON CONFLICT ABORT"));
  }
}
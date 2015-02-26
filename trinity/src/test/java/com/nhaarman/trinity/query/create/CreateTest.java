package com.nhaarman.trinity.query.create;

import com.nhaarman.trinity.query.Executable;
import org.junit.Test;

import static com.nhaarman.trinity.query.create.Column.integer;
import static com.nhaarman.trinity.query.create.Column.numeric;
import static com.nhaarman.trinity.query.create.Column.text;
import static com.nhaarman.trinity.query.create.ConflictAction.FAIL;
import static com.nhaarman.trinity.query.create.Create.create;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

@SuppressWarnings("HardCodedStringLiteral")
public class CreateTest {

  @Test
  public void create_returnsProperPartSql() {
    /* Given */
    Create create = create();

    /* When */
    String partSql = create.getPartSql();

    /* Then */
    assertThat(partSql, is("CREATE"));
  }

  @Test
  public void createFullTable_returnsProperSql() {
    Executable query = create()
        .table("MyTable")
        .columns(
            integer("id").primaryKey(),
            text("name").unique().onConflict(FAIL),
            numeric("age"),
            text("address")
        );

    /* When */
    String sql = query.getSql();

    /* Then */
    assertThat(sql, is("CREATE TABLE MyTable (id INTEGER PRIMARY KEY,name TEXT UNIQUE ON CONFLICT FAIL,age NUMERIC,address TEXT)"));
  }
}
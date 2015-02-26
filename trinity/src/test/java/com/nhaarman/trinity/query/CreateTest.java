package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.query.Create.Column.integer;
import static com.nhaarman.trinity.query.Create.Column.none;
import static com.nhaarman.trinity.query.Create.Column.numeric;
import static com.nhaarman.trinity.query.Create.Column.real;
import static com.nhaarman.trinity.query.Create.Column.text;
import static com.nhaarman.trinity.query.Create.create;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("HardCodedStringLiteral")
public class CreateTest {

  private SQLiteDatabase mDatabaseMock;

  @Before
  public void setUp() {
    mDatabaseMock = mock(SQLiteDatabase.class);
  }

  @Test
  public void create_partSql_returnsProperStatement() {
    /* Given */
    Create create = create();

    /* When */
    String partSql = create.getPartSql();

    /* Then */
    assertThat(partSql, is("CREATE"));
  }

  @Test
  public void createTemporary_partSql_returnsProperStatement() {
    /* Given */
    Create.Temporary temporary = create()
        .temporary();

    /* When */
    String partSql = temporary.getPartSql();

    /* Then */
    assertThat(partSql, is("TEMPORARY"));
  }

  @Test
  public void createTable_partSql_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .table("MyTable");

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("TABLE MyTable"));
  }

  @Test
  public void createTableIfNotExists_partSql_returnsProperStatement() {
     /* Given */
    Create.Table table = create()
        .tableIfNotExists("MyTable");

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("TABLE IF NOT EXISTS MyTable"));
  }

  @Test
  public void createTemporaryTableIfNotExists_partSql_returnsProperStatement() {
    /* Given */
    QueryBase query = create()
        .temporary()
        .tableIfNotExists("MyTable");

    /* When */
    String partSql = query.getPartSql();

    /* Then */
    assertThat(partSql, is("TABLE IF NOT EXISTS MyTable"));
  }

  @Test
  public void createTable_partSql_withSinglePrimaryKeyColumn_returnsProperStatement() {
    /* Given */
    QueryBase query = create()
        .table("MyTable")
        .columns(
            integer("MyColumn").primary()
        );

    /* When */
    String partSql = query.getPartSql();

    /* Then */
    assertThat(partSql, is("(MyColumn INTEGER PRIMARY KEY)"));
  }

  @Test
  public void createTable_partSql_withSingleColumn_returnsProperStatement() {
    /* Given */
    QueryBase query = create()
        .table("MyTable")
        .columns(
            text("MyColumn")
        );

    /* When */
    String partSql = query.getPartSql();

    /* Then */
    assertThat(partSql, is("(MyColumn TEXT)"));
  }

  @Test
  public void createTable_partSql_withMultipleColumns_returnsProperStatement() {
    /* Given */
    QueryBase query = create()
        .table("MyTable")
        .columns(
            integer("MyColumn1"),
            text("MyColumn2"),
            none("MyColumn3"),
            real("MyColumn4"),
            numeric("MyColumn5")
        );

    /* When */
    String partSql = query.getPartSql();

    /* Then */
    assertThat(partSql, is("(MyColumn1 INTEGER,MyColumn2 TEXT,MyColumn3 NONE,MyColumn4 REAL,MyColumn5 NUMERIC)"));
  }

  @Test(expected = MalformedQueryException.class)
  public void createTable_twoEqualColumnNames_throwsException() {
    create()
        .table("MyTable")
        .columns(
            integer("MyColumn"),
            integer("MyColumn")
        );
  }

  @Test
  public void createFullTable_returnsProperStatement() {
    /* Given */
    QueryBase query = create()
        .temporary()
        .table("MyTable")
        .columns(
            integer("MyColumn1"),
            text("MyColumn2"),
            none("MyColumn3"),
            real("MyColumn4"),
            numeric("MyColumn5")
        );

    /* When */
    String sql = query.getSql();

    /* Then */
    assertThat(sql, is("CREATE TEMPORARY TABLE MyTable (MyColumn1 INTEGER,MyColumn2 TEXT,MyColumn3 NONE,MyColumn4 REAL,MyColumn5 NUMERIC)"));
  }

  @Test
  public void executingCreateFullTableStatement_callsDatabase() {
    /* Given */
    ExecutableQuery query = create()
        .temporary()
        .table("MyTable")
        .columns(
            integer("MyColumn1"),
            text("MyColumn2"),
            none("MyColumn3"),
            real("MyColumn4"),
            numeric("MyColumn5")
        );

    /* When */
    query.executeOn(mDatabaseMock);

    /* Then */
    verify(mDatabaseMock).execSQL(eq("CREATE TEMPORARY TABLE MyTable (MyColumn1 INTEGER,MyColumn2 TEXT,MyColumn3 NONE,MyColumn4 REAL,MyColumn5 NUMERIC)"));
  }
}
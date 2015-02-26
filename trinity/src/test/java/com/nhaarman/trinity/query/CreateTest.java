package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.query.Create.Column.Type.INTEGER;
import static com.nhaarman.trinity.query.Create.Column.Type.NONE;
import static com.nhaarman.trinity.query.Create.Column.Type.NUMERIC;
import static com.nhaarman.trinity.query.Create.Column.Type.REAL;
import static com.nhaarman.trinity.query.Create.Column.Type.TEXT;
import static com.nhaarman.trinity.query.Create.create;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
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
    Create create = create()
        .temporary();

    /* When */
    String partSql = create.getPartSql();

    /* Then */
    assertThat(partSql, is("CREATE TEMPORARY"));
  }

  @Test
  public void createTable_partSql_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .table("MyTable");

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("MyTable"));
  }

  @Test
  public void createTable_partSql_withSingleColumn_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .table("MyTable")
        .withColumn("MyColumn").and();

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("MyTable(MyColumn)"));
  }

  @Test
  public void createTable_partSql_withSinglePrimaryKeyColumn_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .table("MyTable")
        .withColumn("MyColumn").withPrimaryKey().getTable();

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("MyTable(MyColumn PRIMARY KEY)"));
  }

  @Test
  public void createTable_partSql_withSingleTypedPrimaryKeyColumn_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .table("MyTable")
        .withColumn("MyColumn").withType(INTEGER).withPrimaryKey().getTable();

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("MyTable(MyColumn INTEGER PRIMARY KEY)"));
  }

  @Test
  public void createTable_partSql_withMultipleColumns_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .table("MyTable")
        .withColumn("MyColumn1")
        .and().withColumn("MyColumn2")
        .and().withColumn("MyColumn3").getTable();

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("MyTable(MyColumn1,MyColumn2,MyColumn3)"));
  }

  @Test
  public void createTable_partSql_withSingleTypedColumn_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .table("MyTable")
        .withColumn("MyColumn").withType(TEXT).getTable();

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("MyTable(MyColumn TEXT)"));
  }

  @Test
  public void createTable_partSql_withMultipleTypedColumns_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .table("MyTable")
        .withColumn("MyColumn1").withType(INTEGER)
        .and().withColumn("MyColumn2").withType(TEXT)
        .and().withColumn("MyColumn3").withType(NONE)
        .and().withColumn("MyColumn4").withType(REAL)
        .and().withColumn("MyColumn5").withType(NUMERIC).getTable();

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("MyTable(MyColumn1 INTEGER,MyColumn2 TEXT,MyColumn3 NONE,MyColumn4 REAL,MyColumn5 NUMERIC)"));
  }

  @Test(expected = MalformedQueryException.class)
  public void createTable_withTwoEqualColumnNames_throwsException() {
    create()
        .table("MyTable")
        .withColumn("MyColumn")
        .and().withColumn("MyColumn");
  }

  @Test
  public void createFullTable_returnsProperStatement() {
    /* Given */
    Create.Table table = create()
        .temporary()
        .table("MyTable")
        .withColumn("MyColumn1").withType(INTEGER)
        .and().withColumn("MyColumn2").withType(TEXT)
        .and().withColumn("MyColumn3").withType(NONE)
        .and().withColumn("MyColumn4").withType(REAL)
        .and().withColumn("MyColumn5").withType(NUMERIC).getTable();

    /* When */
    String sql = table.getSql();

    /* Then */
    assertThat(sql, is("CREATE TEMPORARY MyTable(MyColumn1 INTEGER,MyColumn2 TEXT,MyColumn3 NONE,MyColumn4 REAL,MyColumn5 NUMERIC)"));
  }

  @Test
  public void executingCreateFullTableStatement_callsDatabase() {
    /* Given */
    Create.Table table = create()
        .temporary()
        .table("MyTable")
        .withColumn("MyColumn1").withType(INTEGER)
        .and().withColumn("MyColumn2").withType(TEXT)
        .and().withColumn("MyColumn3").withType(NONE)
        .and().withColumn("MyColumn4").withType(REAL)
        .and().withColumn("MyColumn5").withType(NUMERIC).getTable();

    /* When */
    table.executeOn(mDatabaseMock);

    /* Then */
    verify(mDatabaseMock).execSQL(eq("CREATE TEMPORARY MyTable(MyColumn1 INTEGER,MyColumn2 TEXT,MyColumn3 NONE,MyColumn4 REAL,MyColumn5 NUMERIC)"));
  }
}
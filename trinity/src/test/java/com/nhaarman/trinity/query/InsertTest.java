package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.query.Insert.insert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("HardCodedStringLiteral")
public class InsertTest {

  private SQLiteDatabase mDatabaseMock;

  @Before
  public void setUp() {
    mDatabaseMock = mock(SQLiteDatabase.class);
  }

  @Test
  public void insert_partSql_returnsProperStatement() {
    /* Given */
    Insert insert = insert();

    /* When */
    String partSql = insert.getPartSql();

    /* Then */
    assertThat(partSql, is("INSERT"));
  }

  @Test
  public void insertInto_partSql_returnsProperStatement() {
    /* Given */
    Insert.Into table = insert().into("MyTable");

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("INTO MyTable"));
  }

  @Test
  public void insertIntoWithColumns_partSql_returnsProperStatement() {
    /* Given */
    Insert.Into table = insert().into("MyTable", "Column1", "Column2");

    /* When */
    String partSql = table.getPartSql();

    /* Then */
    assertThat(partSql, is("INTO MyTable(Column1,Column2)"));
  }

  @Test
  public void insertIntoWithColumnsAndValues_partSql_returnsProperStatement() {
    /* Given */
    Insert.Values values = insert().into("MyTable", "Column1", "Column2").values("1", "2");

    /* When */
    String partSql = values.getPartSql();

    /* Then */
    assertThat(partSql, is("VALUES(?,?)"));
  }

  @Test
  public void insertIntoWithColumnsAndValues_partArguments_returnsProperArguments() {
    /* Given */
    Insert.Values values = insert().into("MyTable", "Column1", "Column2").values("1", "2");

    /* When */
    String[] partArguments = values.getPartArguments();

    /* Then */
    assertThat(partArguments, is(new String[] { "1", "2" }));
  }

  @Test
  public void executingInsertStatement_callsDatabase() {
    /* When */
    insert().into("MyTable", "Column1", "Column2").values("1", "2").executeOn(mDatabaseMock);

    /* Then */
    verify(mDatabaseMock).execSQL("INSERT INTO MyTable(Column1,Column2) VALUES(?,?)", new String[] { "1", "2" });
  }

  @Test(expected = MalformedQueryException.class)
  public void executingInsertStatement_withDifferentNumberOfColumnsAndValues_throwsException() {
    insert().into("MyTable", "Column1", "Column2").values("1").executeOn(mDatabaseMock);
  }
}
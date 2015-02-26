package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.query.Delete.delete;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("HardCodedStringLiteral")
public class DeleteTest {

  private SQLiteDatabase mDatabaseMock;

  @Before
  public void setUp() {
    mDatabaseMock = mock(SQLiteDatabase.class);
  }

  @Test
  public void delete_partSql_returnsProperStatement() {
    /* Given */
    Delete delete = delete();

    /* When */
    String partSql = delete.getPartSql();

    /* Then */
    assertThat(partSql, is("DELETE"));
  }

  @Test
  public void deleteFrom_partSql_returnsProperStatement() {
    /* Given */
    Delete.From from = delete().from("MyTable");

    /* When */
    String partSql = from.getPartSql();

    /* Then */
    assertThat(partSql, is("FROM MyTable"));
  }

  @Test
  public void deleteFromWhere_partSql_returnsProperStatement() {
    /* Given */
    Delete.Where where = delete().from("MyTable").where("something");

    /* When */
    String partSql = where.getPartSql();

    /* Then */
    assertThat(partSql, is("WHERE something"));
  }

  @Test
  public void deleteFromWhere_partArguments_returnsProperArguments() {
    /* Given */
    Object[] arguments = { "Test", "Test2" };
    Delete.Where where = delete().from("MyTable").where("something", arguments);

    /* When */
    String[] partArguments = where.getPartArguments();

    /* Then */
    assertThat(partArguments, is(new String[] { "Test", "Test2" }));
  }

  @Test
  public void executingDeleteFromStatement_callsDatabase() {
    /* Given */
    Delete.From from = delete().from("MyTable");

    /* When */
    from.executeOn(mDatabaseMock);

    /* Then */
    verify(mDatabaseMock).execSQL("DELETE FROM MyTable");
  }
}
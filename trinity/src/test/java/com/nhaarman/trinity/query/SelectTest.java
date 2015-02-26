package com.nhaarman.trinity.query;

import android.database.sqlite.SQLiteDatabase;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;

import static com.nhaarman.trinity.query.Select.select;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

@SuppressWarnings("HardCodedStringLiteral")
public class SelectTest {

  private SQLiteDatabase mDatabaseMock;

  @Before
  public void setUp() {
    mDatabaseMock = mock(SQLiteDatabase.class);
  }

  @Test
  public void selectWithoutColumns_partSql_returnsProperStatement() {
    /* Given */
    Select select = select();

    /* When */
    String partSql = select.getPartSql();

    /* Then */
    assertThat(partSql, is("SELECT *"));
  }

  @Test
  public void selectWithASingleColumn_partSql_returnsProperStatement() {
    /* Given */
    Select select = select("Column1");

    /* When */
    String partSql = select.getPartSql();

    /* Then */
    assertThat(partSql, is("SELECT Column1"));
  }

  @Test
  public void selectWithMultipleColumns_partSql_returnsProperStatement() {
    /* Given */
    Select select = select("Column1", "Column2", "Column3");

    /* When */
    String partSql = select.getPartSql();

    /* Then */
    assertThat(partSql, is("SELECT Column1,Column2,Column3"));
  }

  @Test
  public void  selectFrom_partSql_returnsProperStatement() {
    /* Given */
    Select.From from = select().from("MyTable");

    /* When */
    String partSql = from.getPartSql();

    /* Then */
    assertThat(partSql, is("FROM MyTable"));
  }

}
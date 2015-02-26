package com.nhaarman.trinity.query.create;

import org.junit.Test;

import static com.nhaarman.trinity.query.create.OnAction.CASCADE;
import static com.nhaarman.trinity.query.create.OnAction.NO_ACTION;
import static com.nhaarman.trinity.query.create.OnAction.SET_DEFAULT;
import static com.nhaarman.trinity.query.create.OnAction.SET_NULL;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

@SuppressWarnings("HardCodedStringLiteral")
public class ForeignKeyTest {

  @Test
  public void foreignKey_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new ForeignKey(null, "MyTable");

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("REFERENCES MyTable"));
  }

  @Test
  public void foreignKeyWithSingleColumnName_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new ForeignKey(null, "MyTable").columns("Column");

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("REFERENCES MyTable (Column)"));
  }

  @Test
  public void foreignKeyWithMultipleColumnNames_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new ForeignKey(null, "MyTable").columns("Column1", "Column2", "Column3");

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("REFERENCES MyTable (Column1,Column2,Column3)"));
  }

  @Test
  public void foreignKeyOnDeleteSetNull_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new ForeignKey(null, "MyTable").onDelete(SET_NULL);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("REFERENCES MyTable ON DELETE SET NULL"));
  }

  @Test
  public void foreignKeyOnUpdateCascade_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new ForeignKey(null, "MyTable").onUpdate(CASCADE);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("REFERENCES MyTable ON UPDATE CASCADE"));
  }

  @Test
  public void foreignKeyWithMultipleColumnNamesOnDeleteSetDefault_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new ForeignKey(null, "MyTable").columns("Column1", "Column2", "Column3").onDelete(SET_DEFAULT);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("REFERENCES MyTable (Column1,Column2,Column3) ON DELETE SET DEFAULT"));
  }

  @Test
  public void foreignKeyWithMultipleColumnNamesOnUpdateNoAction_returnsProperSql() {
    /* Given */
    ColumnInfo constraint = new ForeignKey(null, "MyTable").columns("Column1", "Column2", "Column3").onUpdate(NO_ACTION);

    /* When */
    String sql = constraint.getSql();

    /* Then */
    assertThat(sql, is("REFERENCES MyTable (Column1,Column2,Column3) ON UPDATE NO ACTION"));
  }
}
package com.nhaarman.trinity.query;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

import static com.nhaarman.trinity.query.Create.Column.Type.INTEGER;
import static com.nhaarman.trinity.query.Create.Column.Type.NONE;
import static com.nhaarman.trinity.query.Create.Column.Type.NUMERIC;
import static com.nhaarman.trinity.query.Create.Column.Type.REAL;
import static com.nhaarman.trinity.query.Create.Column.Type.TEXT;

@SuppressWarnings({ "HardCodedStringLiteral", "PublicInnerClass" })
public class Create extends QueryBase {

  public Create() {
    super(null, null);
  }

  public Temporary temporary() {
    return new Temporary(this, getTableName());
  }

  public Table table(final String tableName) {
    return new Table(this, tableName);
  }

  public TableIfNotExists tableIfNotExists(final String tableName) {
    return new TableIfNotExists(this, tableName);
  }

  @Override
  protected String getPartSql() {
    return "CREATE";
  }

  public static Create create() {
    return new Create();
  }

  public static class Temporary extends ExecutableQueryBase {

    private Temporary(final Query parent, final String table) {
      super(parent, table);
    }

    public Table table(final String tableName) {
      return new Table(this, tableName);
    }

    public TableIfNotExists tableIfNotExists(final String tableName) {
      return new TableIfNotExists(this, tableName);
    }

    @Override
    protected String getPartSql() {
      return "TEMPORARY";
    }
  }

  public static class Table extends ExecutableQueryBase {

    private Table(final Query parent, final String tableName) {
      super(parent, tableName);
    }

    public Columns columns(final Column... columns) {
      return new Columns(this, getTableName(), columns);
    }

    @Override
    protected String getPartSql() {
      return "TABLE " + getTableName();
    }
  }

  public static class TableIfNotExists extends Table {

    private TableIfNotExists(final Query parent, final String tableName) {
      super(parent, tableName);
    }

    @Override
    protected String getPartSql() {
      return "TABLE IF NOT EXISTS " + getTableName();
    }
  }

  public static class Columns extends ExecutableQueryBase {

    private final Collection<Column> mColumns;

    private Columns(final Query parent, final String tableName, final Column[] columns) {
      super(parent, tableName);
      mColumns = new LinkedHashSet<>();
      for (Column column : columns) {
        if (!mColumns.add(column)) {
          throw new MalformedQueryException(String.format("Duplicate column name '%s'", column.mColumnName));
        }
      }
    }

    @Override
    protected String getPartSql() {
      StringBuilder stringBuilder = new StringBuilder(256);
      if (!mColumns.isEmpty()) {
        stringBuilder.append('(');

        Iterator<Column> iterator = mColumns.iterator();
        while (iterator.hasNext()) {
          Column column = iterator.next();

          stringBuilder.append(column.mColumnName);
          if (column.mType != null) {
            stringBuilder.append(' ').append(column.mType);
          }

          if (column.mPrimaryKey) {
            stringBuilder.append(" PRIMARY KEY");
          }

          if (iterator.hasNext()) {
            stringBuilder.append(',');
          }
        }

        stringBuilder.append(')');
      }
      return stringBuilder.toString();
    }
  }

  public static class Column {

    private final String mColumnName;
    private final Type mType;

    private boolean mPrimaryKey;

    public Column(final String columnName, final Type type) {
      mColumnName = columnName;
      mType = type;
    }

    public Column primary() {
      mPrimaryKey = true;
      return this;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Column column = (Column) o;
      return mColumnName.equals(column.mColumnName);
    }

    @Override
    public int hashCode() {
      return mColumnName.hashCode();
    }

    public static Column integer(final String columnName) {
      return new Column(columnName, INTEGER);
    }

    public static Column text(final String columnName) {
      return new Column(columnName, TEXT);
    }

    public static Column none(final String columnName) {
      return new Column(columnName, NONE);
    }

    public static Column real(final String columnName) {
      return new Column(columnName, REAL);
    }

    public static Column numeric(final String columnName) {
      return new Column(columnName, NUMERIC);
    }

    public enum Type {
      INTEGER, TEXT, NONE, REAL, NUMERIC
    }
  }
}

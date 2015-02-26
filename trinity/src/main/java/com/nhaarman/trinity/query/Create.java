package com.nhaarman.trinity.query;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;

@SuppressWarnings({ "HardCodedStringLiteral", "PublicInnerClass" })
public class Create extends QueryBase {

  private boolean mTemporary;

  public Create() {
    super(null, null);
  }

  public Table table(final String table) {
    return new Table(this, table);
  }

  @Override
  protected String getPartSql() {
    return "CREATE" + (mTemporary ? " TEMPORARY" : "");
  }

  public Create temporary() {
    mTemporary = true;
    return this;
  }

  public static Create create() {
    return new Create();
  }

  public static final class Table extends ExecutableQueryBase {

    private final Collection<Column> mColumns;

    private Table(final Create parent, final String table) {
      super(parent, table);
      mColumns = new LinkedHashSet<>();
    }

    public Column withColumn(final String columnName) {
      Column column = new Column(this, columnName);
      if (!mColumns.add(column)) {
        throw new MalformedQueryException(String.format("Column with name '%s' already exists!", columnName));
      }
      return column;
    }

    @Override
    protected String getPartSql() {
      StringBuilder stringBuilder = new StringBuilder(256);

      stringBuilder.append(getTableName());
      if (!mColumns.isEmpty()) {
        stringBuilder.append('(');

        Iterator<Column> iterator = mColumns.iterator();
        while (iterator.hasNext()) {
          Column column = iterator.next();

          stringBuilder.append(column.getColumnName());
          if (column.getType() != null) {
            stringBuilder.append(' ').append(column.getType());
          }

          if (column.isPrimaryKey()) {
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

  public static final class Column extends ExecutableQueryBase {

    private final Table mTable;
    private final String mColumnName;

    private Type mType;
    private boolean mPrimaryKey;

    private Column(final Table table, final String columnName) {
      super(table, table.getTableName());
      mTable = table;
      mColumnName = columnName;
    }

    public Column withType(final Type type) {
      mType = type;
      return this;
    }

    public Table and() {
      return mTable;
    }

    public Table getTable() {
      return mTable;
    }

    String getColumnName() {
      return mColumnName;
    }

    Type getType() {
      return mType;
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

    public Column withPrimaryKey() {
      mPrimaryKey = true;
      return this;
    }

    private boolean isPrimaryKey() {
      return mPrimaryKey;
    }

    public enum Type {
      INTEGER, TEXT, NONE, REAL, NUMERIC
    }
  }
}

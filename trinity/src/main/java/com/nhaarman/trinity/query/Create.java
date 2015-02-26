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

    public Table withColumn(final String column) {
      return withColumn(column, null);
    }

    public Table withColumn(final String columnName, final Column.Type type) {
      if (!mColumns.add(new Column(columnName, type))) {
        throw new MalformedQueryException(String.format("Column with name '%s' already exists!", columnName));
      }
      return this;
    }

    @Override
    protected String getPartSql() {
      StringBuilder stringBuilder = new StringBuilder(256);

      stringBuilder.append(getTable());
      if (!mColumns.isEmpty()) {
        stringBuilder.append('(');

        Iterator<Column> iterator = mColumns.iterator();
        while (iterator.hasNext()) {
          Column column = iterator.next();

          stringBuilder.append(column.getColumnName());
          if (column.getType() != null) {
            stringBuilder.append(' ').append(column.getType());
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

  public static final class Column {

    private final String mColumnName;
    private final Type mType;

    private Column(final String columnName, final Type type) {
      mColumnName = columnName;
      mType = type;
    }

    public String getColumnName() {
      return mColumnName;
    }

    public Type getType() {
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

    public enum Type {
      INTEGER, TEXT, NONE, REAL, NUMERIC
    }
  }
}

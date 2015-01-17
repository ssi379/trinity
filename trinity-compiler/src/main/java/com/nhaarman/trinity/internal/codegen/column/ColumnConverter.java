package com.nhaarman.trinity.internal.codegen.column;

public class ColumnConverter {

    private final TypeConverter mTypeConverter;

    public ColumnConverter() {
        mTypeConverter = new TypeConverter();
    }

    public String toSQLiteStatement(final ColumnInfo columnInfo) {
        StringBuilder stringBuilder = new StringBuilder(128);

        stringBuilder.append(columnInfo.getColumnName());
        stringBuilder.append(' ');
        stringBuilder.append(getType(columnInfo));

        if (columnInfo.getPrimaryKeyInfo() != null) {
            stringBuilder.append(" PRIMARY KEY");

            if (columnInfo.getPrimaryKeyInfo().autoIncrement()) {
                stringBuilder.append(" AUTOINCREMENT");
            }
        }

        if (columnInfo.getForeignInfo() != null) {
            stringBuilder.append(", FOREIGN KEY(");
            stringBuilder.append(columnInfo.getColumnName());
            stringBuilder.append(") REFERENCES ");
            stringBuilder.append(columnInfo.getForeignInfo().tableName());
            stringBuilder.append('(');
            stringBuilder.append(columnInfo.getForeignInfo().columnName());
            stringBuilder.append(')');
        }

        return stringBuilder.toString();
    }

    private String getType(final ColumnInfo columnInfo) {
        if (columnInfo.getForeignInfo() == null) {
            return mTypeConverter.toSQLiteType(columnInfo.getType());
        } else {
            return "INTEGER"; // TODO: Respect foreign key type
        }
    }
}

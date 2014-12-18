package com.nhaarman.ellie.internal.codegen.column;

public class ColumnConverter {

    private final TypeConverter mTypeConverter;

    public ColumnConverter() {
        mTypeConverter = new TypeConverter();
    }

    public String toSQLiteStatement(final ColumnInfo columnInfo) {
        StringBuilder stringBuilder = new StringBuilder(128);

        stringBuilder.append(columnInfo.getColumnName());
        stringBuilder.append(' ');
        stringBuilder.append(mTypeConverter.toSQLiteType(columnInfo.getType()));

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
}

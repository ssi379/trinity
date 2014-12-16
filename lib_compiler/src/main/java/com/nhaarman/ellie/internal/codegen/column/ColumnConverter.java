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
        stringBuilder.append(mTypeConverter.toSQLiteType(columnInfo));

        if (columnInfo.isPrimaryKey()) {
            stringBuilder.append(" PRIMARY KEY");

            if (columnInfo.autoIncrement()) {
                stringBuilder.append(" AUTOINCREMENT");
            }
        }

        if (columnInfo.isForeign()) {
            stringBuilder.append(", FOREIGN KEY(");
            stringBuilder.append(columnInfo.getColumnName());
            stringBuilder.append(") REFERENCES ");
            stringBuilder.append(columnInfo.getForeignTableName());
            stringBuilder.append('(');
            stringBuilder.append(columnInfo.getForeignColumnName());
            stringBuilder.append(')');
        }

        return stringBuilder.toString();
    }
}

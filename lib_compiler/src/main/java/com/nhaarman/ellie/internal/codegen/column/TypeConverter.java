package com.nhaarman.ellie.internal.codegen.column;

public class TypeConverter {

    static final String TYPE_INTEGER = "INTEGER";
    static final String TYPE_REAL = "REAL";
    static final String TYPE_TEXT = "TEXT";


    public String toSQLiteType(final ColumnInfo columnInfo) {
        switch (columnInfo.getType().toString()) {

            case "boolean":
            case "java.lang.Boolean":

            case "int":
            case "java.lang.Integer":

            case "long":
            case "java.lang.Long":

            case "short":
            case "java.lang.Short":

                return TYPE_INTEGER;

            case "double":
            case "java.lang.Double":

            case "float":
            case "java.lang.Float":

                return TYPE_REAL;

            case "java.lang.String":

                return TYPE_TEXT;

            default:
                if (columnInfo.isForeign()) {
                    return TYPE_INTEGER;
                } else {
                    throw new IllegalArgumentException(String.format("Cannot convert type %s to a SQLite type", columnInfo.getType().toString()));
                }
        }
    }
}

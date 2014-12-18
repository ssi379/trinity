package com.nhaarman.ellie.internal.codegen.column;

public class ForeignInfo {

    private final String mTableName;
    private final String mColumnName;

    public ForeignInfo(final String tableName, final String columnName) {
        mTableName = tableName;
        mColumnName = columnName;
    }

    public String tableName() {
        return mTableName;
    }

    public String columnName() {
        return mColumnName;
    }
}

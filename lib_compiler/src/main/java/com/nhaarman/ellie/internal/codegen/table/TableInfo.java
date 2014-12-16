package com.nhaarman.ellie.internal.codegen.table;

import com.nhaarman.ellie.internal.codegen.column.ColumnInfo;

import java.util.Collection;

public class TableInfo {

    private Collection<ColumnInfo> mColumns;
    private String mEntityFQN;
    private String mPackageName;
    private Class<?> mRepositoryClass;
    private int mSinceVersion;
    private String mTableName;

    public String getTableName() {
        return mTableName;
    }

    public void setTableName(final String tableName) {
        mTableName = tableName;
    }

    public int getSinceVersion() {
        return mSinceVersion;
    }

    public void setSinceVersion(final int sinceVersion) {
        mSinceVersion = sinceVersion;
    }

    public Class<?> getRepositoryClass() {
        return mRepositoryClass;
    }

    public void setRepositoryClass(final Class<?> repositoryClass) {
        mRepositoryClass = repositoryClass;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(final String packageName) {
        mPackageName = packageName;
    }

    public String getEntityFQN() {
        return mEntityFQN;
    }

    public void setEntityFQN(final String entityFQN) {
        mEntityFQN = entityFQN;
    }

    public void setColumns(final Collection<ColumnInfo> columns) {
        mColumns = columns;
    }

    public Collection<ColumnInfo> getColumns() {
        return mColumns;
    }
}

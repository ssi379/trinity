package com.nhaarman.ellie.internal.codegen.column;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

public class ColumnInfo {

    private TypeMirror mType;
    private String mColumnName;
    private ExecutableElement mGetter;
    private ExecutableElement mSetter;
    private boolean mIsForeignColumn;
    private boolean mPrimaryKey;
    private boolean mAutoIncrement;
    private String mForeignTable;
    private String mForeignColumnName;

    public TypeMirror getType() {
        return mType;
    }

    public void setType(final TypeMirror type) {
        mType = type;
    }

    public String getColumnName() {
        return mColumnName;
    }

    public void setColumnName(final String columnName) {
        mColumnName = columnName;
    }

    public ExecutableElement getGetter() {
        return mGetter;
    }

    public void setGetter(final ExecutableElement getter) {
        mGetter = getter;
    }

    public ExecutableElement getSetter() {
        return mSetter;
    }

    public void setSetter(final ExecutableElement setter) {
        mSetter = setter;
    }

    public boolean isForeign() {
        return mIsForeignColumn;
    }

    public boolean isPrimaryKey() {
        return mPrimaryKey;
    }

    public void setPrimaryKey(final boolean primaryKey) {
        mPrimaryKey = primaryKey;
    }

    public boolean autoIncrement() {
        return mAutoIncrement;
    }

    public void setAutoIncrement(final boolean autoIncrement) {
        mAutoIncrement = autoIncrement;
    }

    public String getForeignTableName() {
        return mForeignTable;
    }

    public void setForeignTable(final String foreignTable) {
        mForeignTable = foreignTable;
    }

    public String getForeignColumnName() {
        return mForeignColumnName;
    }

    public void setForeignColumnName(final String foreignColumnName) {
        mForeignColumnName = foreignColumnName;
    }

    public void setForeignColumn(final boolean foreignColumn) {
        mIsForeignColumn = foreignColumn;
    }
}

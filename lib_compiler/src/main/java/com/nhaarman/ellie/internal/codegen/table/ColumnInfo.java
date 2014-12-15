package com.nhaarman.ellie.internal.codegen.table;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

public class ColumnInfo {

    private TypeMirror mType;
    private String mColumnName;
    private ExecutableElement mGetter;
    private ExecutableElement mSetter;
    private boolean mIsForeignColumn;

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

    public void setForeignColumn(final boolean foreignColumn) {
        mIsForeignColumn = foreignColumn;
    }

    public boolean isForeign() {
        return mIsForeignColumn;
    }
}

package com.nhaarman.ellie.internal.codegen.column;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.lang.model.type.TypeMirror;

public class ColumnInfo {

    private final String mColumnName;

    private final Collection<ColumnMethodInfo> mMethods = new ArrayList<>();

    public ColumnInfo(final String columnName, final ColumnMethodInfo methodInfo) {
        mColumnName = columnName;
        mMethods.add(methodInfo);
    }

    public void addMethodInfo(final ColumnMethodInfo info) {
        mMethods.add(info);
    }

    public String getColumnName() {
        return mColumnName;
    }

    public Collection<ColumnMethodInfo> getMethodInfos() {
        return Collections.unmodifiableCollection(mMethods);
    }

    public TypeMirror getType() {
        return mMethods.iterator().next().getType();
    }

    public ForeignInfo getForeignInfo() {
        for (ColumnMethodInfo method : mMethods) {
            if (method.getForeignInfo() != null) {
                return method.getForeignInfo();
            }
        }
        return null;
    }

    public PrimaryKeyInfo getPrimaryKeyInfo() {
        for (ColumnMethodInfo method : mMethods) {
            if (method.getPrimaryKeyInfo() != null) {
                return method.getPrimaryKeyInfo();
            }
        }
        return null;
    }

}
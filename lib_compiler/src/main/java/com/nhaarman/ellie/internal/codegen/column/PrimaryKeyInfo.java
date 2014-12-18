package com.nhaarman.ellie.internal.codegen.column;

public class PrimaryKeyInfo {

    private final boolean mAutoIncrement;

    public PrimaryKeyInfo(final boolean autoIncrement) {
        mAutoIncrement = autoIncrement;
    }

    public boolean autoIncrement() {
        return mAutoIncrement;
    }
}

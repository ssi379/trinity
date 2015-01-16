package com.nhaarman.lib_setup.migrations;

public interface Migration extends Comparable<Migration> {

    int getVersion();

    void beforeUp();

    String[] getUpStatements();

    void afterUp();

    void beforeDown();

    String[] getDownStatements();

    void afterDown();
}

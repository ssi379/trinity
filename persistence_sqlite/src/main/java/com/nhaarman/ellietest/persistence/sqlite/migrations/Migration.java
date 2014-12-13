package com.nhaarman.ellietest.persistence.sqlite.migrations;

public interface Migration extends Comparable<Migration> {

    int getVersion();

    long getOrder();

    void beforeUp();

    String[] getUpStatements();

    void afterUp();

    void beforeDown();

    String[] getDownStatements();

    void afterDown();
}

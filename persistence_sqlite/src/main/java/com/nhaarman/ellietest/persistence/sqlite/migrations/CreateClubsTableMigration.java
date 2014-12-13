package com.nhaarman.ellietest.persistence.sqlite.migrations;

public class CreateClubsTableMigration extends MigrationAdapter {

    private static final long ORDER = 20141213194024L;

    public CreateClubsTableMigration() {
        super(1);
    }

    @Override
    public long getOrder() {
        return ORDER;
    }

    @Override
    public String[] getUpStatements() {
        return new String[]{
                "CREATE TABLE clubs (" +
                        "id INTEGER PRIMARY KEY NOT NULL," +
                        "name STRING" +
                        ")"
        };
    }
}

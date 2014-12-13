package com.nhaarman.ellietest.persistence.sqlite.migrations;

public class CreateTeamsTableMigration extends MigrationAdapter {

    private static final long ORDER = 20141213195014L;

    public CreateTeamsTableMigration() {
        super(2);
    }

    @Override
    public long getOrder() {
        return ORDER;
    }

    @Override
    public String[] getUpStatements() {
        return new String[]{
                "CREATE TABLE teams (" +
                        "id INTEGER PRIMARY KEY NOT NULL," +
                        "name STRING," +
                        "club_id INTEGER," +
                        "FOREIGN KEY(club_id) REFERENCES clubs(id)" +
                        ")"
        };
    }
}

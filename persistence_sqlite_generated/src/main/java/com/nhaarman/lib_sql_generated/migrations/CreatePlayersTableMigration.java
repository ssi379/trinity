package com.nhaarman.lib_sql_generated.migrations;

import com.nhaarman.lib_setup.migrations.MigrationAdapter;

public class CreatePlayersTableMigration extends MigrationAdapter {

    private static final long ORDER = 201412131955551L;

    public CreatePlayersTableMigration() {
        super(2);
    }

    @Override
    public long getOrder() {
        return ORDER;
    }

    @Override
    public String[] getUpStatements() {
        return new String[]{
                "CREATE TABLE players (" +
                        "id INTEGER PRIMARY KEY NOT NULL," +
                        "name STRING," +
                        "team_id INTEGER," +
                        "FOREIGN KEY(team_id) REFERENCES teams(id)" +
                        ")"
        };
    }
}

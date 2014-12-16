package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.lib_setup.annotations.Migration;
import com.nhaarman.lib_setup.migrations.MigrationAdapter;

@Migration(version = 1, order = CreateClubsTableMigration.ORDER)
public class CreateClubsTableMigration extends MigrationAdapter {

    static final long ORDER = 20141213194024L;

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

package com.nhaarman.lib_sql_generated.migrations;

import com.nhaarman.ellietest.persistence.sqlite.CreateClubsTableMigration;
import com.nhaarman.sql_lib.migrations.Migrations;

import javax.inject.Inject;

public class MigrationFactory {

    @Inject
    public MigrationFactory() {
    }

    public Migrations createMigrations() {
        Migrations migrations = new Migrations();

        migrations.addMigration(new CreateClubsTableMigration());
        migrations.addMigration(new CreateTeamsTableMigration());
        migrations.addMigration(new CreatePlayersTableMigration());

        return migrations;
    }

}

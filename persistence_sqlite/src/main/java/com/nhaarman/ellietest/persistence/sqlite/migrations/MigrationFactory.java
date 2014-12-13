package com.nhaarman.ellietest.persistence.sqlite.migrations;

import com.nhaarman.sql_lib.migrations.IMigrationFactory;
import com.nhaarman.sql_lib.migrations.Migrations;

import javax.inject.Inject;

public class MigrationFactory implements IMigrationFactory {

    @Inject
    public MigrationFactory() {
    }

    @Override
    public Migrations createMigrations() {
        Migrations migrations = new Migrations();

        migrations.addMigration(new CreateClubsTableMigration());
        migrations.addMigration(new CreateTeamsTableMigration());
        migrations.addMigration(new CreatePlayersTableMigration());

        return migrations;
    }

}

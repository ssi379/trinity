package com.nhaarman.ellietest.persistence.sqlite.migrations;

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

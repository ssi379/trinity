package com.nhaarman.trinity.migrations;

import java.util.SortedSet;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class MigrationsTest {

  private Migrations mMigrations;

  @Before
  public void setUp() {
    mMigrations = new Migrations();
  }

  @Test
  public void initially_migrationsAreEmpty() {
    /* When */
    SortedSet<Migration> migrations = mMigrations.getMigrationsForVersion(0);

    /* Then */
    assertThat(migrations, is(emptyCollectionOf(Migration.class)));
  }

  @Test
  public void afterAddingAMigration_itIsPresentInThatVersionsMigration() {
    /* Given */
    Migration migration = new MigrationAdapter(1);
    mMigrations.addMigration(migration);

    /* When */
    SortedSet<Migration> migrations = mMigrations.getMigrationsForVersion(1);

    /* Then */
    assertThat(migrations, contains(migration));
  }

  @Test
  public void afterAddingAMigration_itIsNotPresentInAnotherVersionsMigration() {
    /* Given */
    Migration migration = new MigrationAdapter(5);
    mMigrations.addMigration(migration);

    /* When */
    SortedSet<Migration> migrations = mMigrations.getMigrationsForVersion(0);

    /* Then */
    assertThat(migrations, not(contains(migration)));
  }

  @Test
  public void getVersionNumber_returnsProperVersionNumber() {
    /* Given */
    Migration migration1 = new MigrationAdapter(1);
    mMigrations.addMigration(migration1);

    Migration migration5 = new MigrationAdapter(5);
    mMigrations.addMigration(migration5);

    Migration migration3 = new MigrationAdapter(3);
    mMigrations.addMigration(migration3);

    /* When */
    int versionNumber = mMigrations.getVersionNumber();

    /* Then */
    assertThat(versionNumber, is(5));
  }

  @Test
  public void addingMigrationsInNonSortedOrder_returnsSortedMigrations() {
    /* Given */
    Migration migration1 = new MigrationAdapter(1, 1);
    mMigrations.addMigration(migration1);

    Migration migration2 = new MigrationAdapter(1, 3);
    mMigrations.addMigration(migration2);

    Migration migration3 = new MigrationAdapter(1, 2);
    mMigrations.addMigration(migration3);

    /* When */
    SortedSet<Migration> migrations = mMigrations.getMigrationsForVersion(1);

    /* Then */
    assertThat(migrations, contains(migration1, migration3, migration2));
  }
}
package com.nhaarman.trinity.migrations;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MigrationsTest {

  private Migrations mMigrations;

  @Before
  public void setUp() {
    mMigrations = new Migrations();
  }

  @Test
  public void initially_migrationsAreEmpty() {
    /* When */
    List<Migration> migrations = mMigrations.getMigrationsForVersion(0);

    /* Then */
    assertThat(migrations, is(emptyCollectionOf(Migration.class)));
  }

  @Test
  public void afterAddingAMigration_itIsPresentInThatVersionsMigration() {
    /* Given */
    Migration migration = mock(Migration.class);
    when(migration.getVersion()).thenReturn(1);
    mMigrations.addMigration(migration);

    /* When */
    List<Migration> migrations = mMigrations.getMigrationsForVersion(1);

    /* Then */
    assertThat(migrations, contains(migration));
  }

  @Test
  public void afterAddingAMigration_itIsNotPresentInAnotherVersionsMigration() {
    /* Given */
    Migration migration = mock(Migration.class);
    when(migration.getVersion()).thenReturn(1);
    mMigrations.addMigration(migration);

    /* When */
    List<Migration> migrations = mMigrations.getMigrationsForVersion(0);

    /* Then */
    assertThat(migrations, not(contains(migration)));
  }

  @Test
  public void getVersionNumber_returnsProperVersionNumber() {
     /* Given */
    Migration migration1 = mock(Migration.class);
    when(migration1.getVersion()).thenReturn(1);
    mMigrations.addMigration(migration1);

    Migration migration5 = mock(Migration.class);
    when(migration5.getVersion()).thenReturn(5);
    mMigrations.addMigration(migration5);

    Migration migration3 = mock(Migration.class);
    when(migration3.getVersion()).thenReturn(3);
    mMigrations.addMigration(migration3);

    /* When */
    int versionNumber = mMigrations.getVersionNumber();

    /* Then */
    assertThat(versionNumber, is(5));
  }
}
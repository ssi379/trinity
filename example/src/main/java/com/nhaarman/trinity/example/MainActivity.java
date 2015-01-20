package com.nhaarman.trinity.example;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.nhaarman.trinity.SQLiteDatabaseHelper;
import com.nhaarman.trinity.migrations.Migrations;

public class MainActivity extends Activity {

  private ClubRepository mClubRepository;

  private TeamRepository mTeamRepository;

  private PlayerRepository mPlayerRepository;

  private Long mPlayerId;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    Migrations migrations = new Migrations();
    migrations.addMigration(new CreateClubsTableMigration());
    migrations.addMigration(new CreateTeamsTableMigration());
    migrations.addMigration(new CreatePlayersTableMigration());

    SQLiteDatabaseHelper databaseHelper = new SQLiteDatabaseHelper(this, migrations);
    SQLiteDatabase database = databaseHelper.getWritableDatabase();

    mClubRepository = new com.nhaarman.trinity.example.Trinity_ClubRepository(database);
    mTeamRepository = new com.nhaarman.trinity.example.Trinity_TeamRepository(database);
    mPlayerRepository = new com.nhaarman.trinity.example.Trinity_PlayerRepository(database);
  }

  @Override
  protected void onStart() {
    super.onStart();
    Club club = new Club();
    club.setName("Club");
    Long clubId = mClubRepository.create(club);

    Team team = new Team();
    team.setName("Team");
    team.setClubId(clubId);
    Long teamId = mTeamRepository.create(team);

    Player player = new Player();
    player.setName("Player");
    player.setTeamId(teamId);
    mPlayerId = mPlayerRepository.create(player);
  }

  @Override
  protected void onResume() {
    super.onResume();
    Player player = mPlayerRepository.find(mPlayerId);
    Team team = mTeamRepository.find(player.getTeamId());
    Club club = mClubRepository.find(team.getClubId());

    Toast.makeText(this, club.getName() + ", " + team.getName() + ", " + player.getName(), Toast.LENGTH_LONG).show();
  }
}

/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.sample;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import com.nhaarman.trinity.TrinitySQLiteOpenHelper;
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

    TrinitySQLiteOpenHelper databaseHelper = new TrinitySQLiteOpenHelper(this, "Sample", migrations);
    SQLiteDatabase database = databaseHelper.getWritableDatabase();

    mClubRepository = new com.nhaarman.trinity.sample.TrinityClubRepository(database);
    mTeamRepository = new com.nhaarman.trinity.sample.TrinityTeamRepository(database);
    mPlayerRepository = new com.nhaarman.trinity.sample.TrinityPlayerRepository(database);
  }

  @Override
  protected void onStart() {
    super.onStart();
    Club club = new Club();
    club.setName("Club");
    club.setId("Club");
    mClubRepository.create(club);

    Team team = new Team();
    team.setName("Team");
    team.setClubId("Club");
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

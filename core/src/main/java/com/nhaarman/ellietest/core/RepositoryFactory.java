package com.nhaarman.ellietest.core;

import com.nhaarman.ellietest.core.clubs.ClubRepository;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.TeamRepository;

public interface RepositoryFactory {

    ClubRepository createClubRepository();

    TeamRepository createTeamRepository();

    PlayerRepository createPlayerRepository();

}

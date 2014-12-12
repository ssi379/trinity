package com.nhaarman.ellietest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.nhaarman.ellietest.core.players.Player;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.Team;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.ellietest.persistence.Dagger_MemoryPersistenceComponent;

import javax.inject.Inject;

public class MainActivity extends Activity {

    @Inject
    TeamRepository mTeamRepository;

    @Inject
    PlayerRepository mPlayerRepository;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMyApplication().getApplicationComponent().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Team team = new Team();
        team.setName("Name1");

        Player player = new Player();
        player.setTeam(team);
        player.setName("Player");
        mPlayerRepository.create(player);

        String name = mTeamRepository.find(team.getId()).getName();
        ((TextView) findViewById(R.id.a)).setText(name);
    }

    MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

}

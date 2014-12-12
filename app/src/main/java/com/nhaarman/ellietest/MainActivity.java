package com.nhaarman.ellietest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nhaarman.ellietest.core.teams.Team;
import com.nhaarman.ellietest.core.teams.TeamRepository;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TeamRepository teamRepository = getMyApplication().getRepositoryFactory().createTeamRepository();

        Team team = new Team();
        team.setName("Name");
        Long id = teamRepository.create(team);

        String name = teamRepository.find(id).getName();
        System.out.println(name);
    }

    MyApplication getMyApplication() {
        return (MyApplication) getApplication();
    }

}

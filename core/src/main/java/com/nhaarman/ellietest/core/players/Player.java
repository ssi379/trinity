package com.nhaarman.ellietest.core.players;

import com.nhaarman.ellietest.core.teams.Team;

public class Player {

    private Long mId;

    private String mName;

    private Team mTeam;

    public Long getId() {
        return mId;
    }

    public void setId(final Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public Team getTeam() {
        return mTeam;
    }

    public void setTeam(final Team team) {
        mTeam = team;
    }
}

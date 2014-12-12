package com.nhaarman.ellietest.core.teams;

import com.nhaarman.ellietest.core.clubs.Club;

public class Team {

    private Long mId;

    private String mName;

    private Club mClub;

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

    public Club getClub() {
        return mClub;
    }

    public void setClub(final Club club) {
        mClub = club;
    }
}

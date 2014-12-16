package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.ellietest.core.clubs.Club;
import com.nhaarman.ellietest.core.players.Player;
import com.nhaarman.ellietest.core.teams.Team;
import com.nhaarman.ellietest.core.teams.TeamRepository;
import com.nhaarman.lib_setup.Column;
import com.nhaarman.lib_setup.Foreign;
import com.nhaarman.lib_setup.Table;

@Table(name = "teams", repository = TeamRepository.class)
public class SQLTeam extends Team {

    @Override
    @Column("id")
    public Long getId() {
        return super.getId();
    }

    @Override
    @Column("id")
    public void setId(final Long id) {
        super.setId(id);
    }

    @Override
    @Column("name")
    public String getName() {
        return super.getName();
    }

    @Override
    @Column("name")
    public void setName(final String name) {
        super.setName(name);
    }

    @Override
    @Foreign(tableName = "clubs", columnName = "id")
    @Column("club_id")
    public Club getClub() {
        return super.getClub();
    }

    @Override
    @Column("club_id")
    public void setClub(final Club club) {
        super.setClub(club);
    }
}

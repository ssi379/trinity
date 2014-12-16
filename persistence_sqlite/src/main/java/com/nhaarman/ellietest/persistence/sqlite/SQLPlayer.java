package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.ellietest.core.players.Player;
import com.nhaarman.ellietest.core.players.PlayerRepository;
import com.nhaarman.ellietest.core.teams.Team;
import com.nhaarman.lib_setup.annotations.Column;
import com.nhaarman.lib_setup.annotations.Foreign;
import com.nhaarman.lib_setup.annotations.PrimaryKey;
import com.nhaarman.lib_setup.annotations.Table;

@Table(name = "players", repository = PlayerRepository.class, sinceVersion = 3)
public class SQLPlayer extends Player {

    @Override
    @PrimaryKey
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
    @Foreign(tableName = "teams", columnName = "id")
    @Column("team_id")
    public Team getTeam() {
        return super.getTeam();
    }

    @Override
    @Column("team_id")
    public void setTeam(final Team team) {
        super.setTeam(team);
    }
}

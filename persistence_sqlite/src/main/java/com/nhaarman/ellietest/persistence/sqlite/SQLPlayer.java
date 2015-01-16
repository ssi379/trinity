package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.ellietest.core.Player;
import com.nhaarman.ellietest.core.Team;
import com.nhaarman.lib_setup.annotations.Column;
import com.nhaarman.lib_setup.annotations.Foreign;
import com.nhaarman.lib_setup.annotations.PrimaryKey;
import com.nhaarman.lib_setup.annotations.Table;

@Table(name = "players")
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
  public Long getTeamId() {
    return super.getTeamId();
  }

  @Override
  @Column("team_id")
  public void setTeamId(final Long teamId) {
    super.setTeamId(teamId);
  }
}

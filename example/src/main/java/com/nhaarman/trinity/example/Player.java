package com.nhaarman.trinity.example;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.Foreign;
import com.nhaarman.trinity.annotations.PrimaryKey;
import com.nhaarman.trinity.annotations.Table;

@Table(name = "players")
public class Player {

  private Long mId;

  private String mName;

  private Long mTeamId;

  @PrimaryKey
  @Column("id")
  public Long getId() {
    return mId;
  }

  @Column("id")
  public void setId(final Long id) {
    mId = id;
  }

  @Column("name")
  public String getName() {
    return mName;
  }

  @Column("name")
  public void setName(final String name) {
    mName = name;
  }

  @Foreign(tableName = "teams", columnName = "id")
  @Column("team_id")
  public Long getTeamId() {
    return mTeamId;
  }

  @Column("team_id")
  public void setTeamId(final Long teamId) {
    mTeamId = teamId;
  }
}

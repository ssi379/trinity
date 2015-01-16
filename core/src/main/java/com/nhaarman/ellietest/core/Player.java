package com.nhaarman.ellietest.core;

public class Player {

  private Long mId;

  private String mName;

  private Long mTeamId;

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

  public Long getTeamId() {
    return mTeamId;
  }

  public void setTeamId(final Long teamId) {
    mTeamId = teamId;
  }
}

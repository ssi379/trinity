package com.nhaarman.ellietest.core;

public class Team {

  private Long mId;

  private String mName;

  private Long mClubId;

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

  public Long getClubId() {
    return mClubId;
  }

  public void setClubId(final Long clubId) {
    mClubId = clubId;
  }
}

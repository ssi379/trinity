package com.nhaarman.trinity.example;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.Foreign;
import com.nhaarman.trinity.annotations.PrimaryKey;
import com.nhaarman.trinity.annotations.Table;

@Table(name = "teams")
public class Team {


  private Long mId;

  private String mName;

  private Long mClubId;

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

  @Foreign(tableName = "clubs", columnName = "id")
  @Column("club_id")
  public Long getClubId() {
    return mClubId;
  }

  @Column("club_id")
  public void setClubId(final Long clubId) {
    mClubId = clubId;
  }
}

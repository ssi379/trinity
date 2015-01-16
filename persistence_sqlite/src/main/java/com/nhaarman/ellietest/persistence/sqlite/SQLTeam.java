package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.ellietest.core.Team;
import com.nhaarman.lib_setup.annotations.Column;
import com.nhaarman.lib_setup.annotations.Foreign;
import com.nhaarman.lib_setup.annotations.PrimaryKey;
import com.nhaarman.lib_setup.annotations.Table;

@Table(name = "teams")
public class SQLTeam extends Team {

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
  @Foreign(tableName = "clubs", columnName = "id")
  @Column("club_id")
  public Long getClubId() {
    return super.getClubId();
  }

  @Override
  @Column("club_id")
  public void setClubId(final Long clubId) {
    super.setClubId(clubId);
  }
}

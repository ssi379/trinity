package com.nhaarman.trinity.sample;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.PrimaryKey;
import com.nhaarman.trinity.annotations.Table;

@Table(name = "clubs")
public class Club {

  private Long mId;

  private String mName;

  @Column("id")
  @PrimaryKey
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
}

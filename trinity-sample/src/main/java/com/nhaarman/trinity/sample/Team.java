/*
 * Copyright 2015 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.trinity.sample;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.Foreign;
import com.nhaarman.trinity.annotations.PrimaryKey;
import com.nhaarman.trinity.annotations.Table;

@Table(name = "teams")
public class Team {

  private Long mId;

  private String mName;

  private String mClubId;

  @PrimaryKey
  @Column("id")
  public Long getId() {
    return mId;
  }

  @Column("id")
  @PrimaryKey
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
  public String getClubId() {
    return mClubId;
  }

  @Column("club_id")
  public void setClubId(final String clubId) {
    mClubId = clubId;
  }
}

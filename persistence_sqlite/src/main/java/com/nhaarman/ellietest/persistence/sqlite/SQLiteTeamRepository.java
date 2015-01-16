package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.ellietest.core.Team;
import com.nhaarman.ellietest.core.TeamRepository;
import com.nhaarman.lib_setup.annotations.Repository;

@Repository(tableName = "teams")
public interface SQLiteTeamRepository extends TeamRepository {

  @Override
  Team find(Long id);
}

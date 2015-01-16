package com.nhaarman.ellietest.persistence.sqlite;

import com.nhaarman.ellietest.core.Player;
import com.nhaarman.ellietest.core.PlayerRepository;
import com.nhaarman.lib_setup.annotations.Repository;

@Repository(tableName = "players")
public interface SQLitePlayerRepository extends PlayerRepository {

  @Override
  Player find(Long id);
}

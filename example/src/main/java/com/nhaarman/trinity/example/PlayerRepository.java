package com.nhaarman.trinity.example;

import com.nhaarman.trinity.annotations.Repository;

@Repository(tableName = "players")
public interface PlayerRepository {

  Player find(Long id);

  Long create(Player object);
}

package com.nhaarman.trinity.sample;

import com.nhaarman.trinity.annotations.Repository;

@Repository(Player.class)
public interface PlayerRepository {

  Player find(Long id);

  Long create(Player player);
}

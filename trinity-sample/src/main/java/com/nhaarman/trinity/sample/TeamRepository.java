package com.nhaarman.trinity.sample;

import com.nhaarman.trinity.annotations.Repository;

@Repository(Team.class)
public interface TeamRepository {

  Team find(Long id);

  Long create(Team object);
}

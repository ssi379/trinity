package com.nhaarman.trinity.example;

import com.nhaarman.trinity.annotations.Repository;

@Repository(Club.class)
public interface ClubRepository {

  Club find(Long id);

  Long create(Club club);

}

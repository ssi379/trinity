package com.nhaarman.trinity.example;

import com.nhaarman.trinity.annotations.Repository;

@Repository(tableName = "clubs")
public interface ClubRepository {

  Club find(Long id);

  Long create(Club club);

}

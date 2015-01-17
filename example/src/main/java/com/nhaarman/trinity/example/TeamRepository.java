package com.nhaarman.trinity.example;

import com.nhaarman.trinity.annotations.Repository;

@Repository(tableName = "teams")
public interface TeamRepository {

  Team find(Long id);

  Long create(Team object);
}

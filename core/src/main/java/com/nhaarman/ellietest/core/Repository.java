package com.nhaarman.ellietest.core;

public interface Repository<T> {

    Long create(T object);

    boolean update(T object);

    T find(Long id);
}

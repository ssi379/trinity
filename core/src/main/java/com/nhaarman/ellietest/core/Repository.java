package com.nhaarman.ellietest.core;

public interface Repository<T> {

    Long create(T object);

    void update(T object);

    T find(Long id);
}

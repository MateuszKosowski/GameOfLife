package org.team1;

public interface Dao<T> {
    T read();
    void write(T object);
}

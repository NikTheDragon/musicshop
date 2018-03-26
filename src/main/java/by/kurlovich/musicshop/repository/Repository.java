package by.kurlovich.musicshop.repository;

import by.kurlovich.musicshop.repository.exception.RepositoryException;

public interface Repository<T> {
    boolean add(T item) throws RepositoryException;
}

package by.kurlovich.musicshop.repository;

import java.util.List;

public interface Repository<T> {
    enum Status {
        ACTIVE,
        DELETE,
        NA;
    }

    void add(T item) throws RepositoryException;
    void delete (T item) throws RepositoryException;
    void update (T item) throws RepositoryException;

    List<T> query(Specification specification) throws RepositoryException;
    Status checkStatus(T item) throws RepositoryException;
    void setStatus (T item) throws RepositoryException;
}

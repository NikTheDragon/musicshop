package by.kurlovich.musicshop.repository;

public interface Repository<T> {
    boolean add(T item) throws RepositoryException;

    boolean isExists(IsExistsSpecification specification) throws RepositoryException;
}

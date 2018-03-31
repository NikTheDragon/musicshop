package by.kurlovich.musicshop.repository;

public interface IsExistsSpecification {
    boolean exists() throws RepositoryException;
}

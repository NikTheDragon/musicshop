package by.kurlovich.musicshop.repository;

import java.util.List;

public interface Repository<T> {
    enum Status {
        ACTIVE,
        DELETE,
        NA;

        public static Status getStatus(String value) {
            if (value == null) {
                value = "";
            }

            switch (value) {
                case "active":
                    return Status.ACTIVE;
                case "deleted":
                    return Status.DELETE;
                default:
                    return Status.NA;
            }
        }
    }

    void add(T item) throws RepositoryException;
    void delete(T item) throws RepositoryException;
    void update(T item) throws RepositoryException;
    List<T> query(Specification specification) throws RepositoryException;
    List<T> queryWithOwners(Specification specification) throws RepositoryException;
    Status getStatus(T item) throws RepositoryException;
    void undelete(T item) throws RepositoryException;
    void buy(Specification specification) throws RepositoryException;
}

package by.kurlovich.musicshop.repository;

import by.kurlovich.musicshop.entity.User;

import java.util.List;

public interface UserRepository {
    void add(User user) throws RepositoryException;
    void update(User user) throws RepositoryException;
    void updatePassword(String password, String userId) throws RepositoryException;
    List<User> query(Specification specification) throws RepositoryException;
}

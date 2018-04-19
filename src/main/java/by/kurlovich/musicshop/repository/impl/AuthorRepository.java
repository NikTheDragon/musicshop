package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;

import java.util.List;

public class AuthorRepository implements Repository<Author> {

    @Override
    public void add(Author item) throws RepositoryException {

    }

    @Override
    public void delete(Author item) throws RepositoryException {

    }

    @Override
    public void update(Author item) throws RepositoryException {

    }

    @Override
    public List<Author> query(Specification specification) throws RepositoryException {
        return null;
    }

    @Override
    public Status checkStatus(Author item) throws RepositoryException {
        return null;
    }

    @Override
    public void setStatus(Author item) throws RepositoryException {

    }
}

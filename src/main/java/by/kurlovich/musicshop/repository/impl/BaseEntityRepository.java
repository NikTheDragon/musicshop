package by.kurlovich.musicshop.repository.impl;

import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionException;
import by.kurlovich.musicshop.repository.dbconnection.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public abstract class BaseEntityRepository<T> implements EntityRepository<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityRepository.class);

    private final ConnectionPool pool;

    BaseEntityRepository() throws RepositoryException {
        try {
            pool = ConnectionPool.getInstance();
        } catch (ConnectionException e) {
            throw new RepositoryException("Can't create dbconnection pool", e);
        }
    }

    protected Connection getConnection() throws ConnectionException {
        return pool.getConnection();
    }
}

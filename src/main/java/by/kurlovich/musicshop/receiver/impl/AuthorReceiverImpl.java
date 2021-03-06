package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.AuthorRepositoryImpl;
import by.kurlovich.musicshop.repository.specification.GetAllAuthorsSpecification;
import by.kurlovich.musicshop.repository.specification.SearchAuthorsSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class AuthorReceiverImpl implements EntityReceiver<Author> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorReceiverImpl.class);

    @Override
    public boolean addNewEntity(Author author) throws ReceiverException {
        try {
            EntityRepository<Author> entityRepository = new AuthorRepositoryImpl();
            LOGGER.debug("trying to add author: {}", author.getName());

            if (author.getName().isEmpty()) {
                return false;
            }

            switch (entityRepository.getStatus(author)) {
                case ACTIVE:
                    LOGGER.debug("found active author: {}", author.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted author: {}", author.getName());
                    entityRepository.undelete(author);
                    return true;
                case NA:
                    entityRepository.add(author);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewAuthor.\n" + e, e);
        }
    }

    @Override
    public boolean deleteEntity(Author author) throws ReceiverException {
        try {
            EntityRepository<Author> entityRepository = new AuthorRepositoryImpl();
            LOGGER.debug("trying to delete track: {}", author.getName());

            if (author.getName().isEmpty()) {
                return false;
            }

            entityRepository.delete(author);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteAuthor.\n"+e, e);
        }
    }

    @Override
    public boolean updateEntity(Author author) throws ReceiverException {
        try {
            EntityRepository<Author> entityRepository = new AuthorRepositoryImpl();
            LOGGER.debug("trying to update author: {}", author.getName());

            if (author.getName().isEmpty()) {
                return false;
            }

            entityRepository.update(author);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateTrack.\n"+e, e);
        }
    }

    @Override
    public List<Author> getAllEntities() throws ReceiverException {
        try {
            EntityRepository<Author> entityRepository = new AuthorRepositoryImpl();
            Specification specification = new GetAllAuthorsSpecification();
            LOGGER.debug("Trying to get all authors.");

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in Get All Authors.\n" + e, e);
        }
    }

    @Override
    public List<Author> getSearchedEntities(SearchData searchData, String userId) throws ReceiverException {
        try {
            EntityRepository<Author> entityRepository = new AuthorRepositoryImpl();
            Specification specification = new SearchAuthorsSpecification(searchData);
            LOGGER.debug("trying to search authors.");

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSearchedEntities of AuthorReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Author> getSpecifiedEntities(String param) throws ReceiverException {
        return Collections.emptyList();
    }
}

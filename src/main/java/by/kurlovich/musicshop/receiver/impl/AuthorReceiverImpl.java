package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.AuthorRepository;
import by.kurlovich.musicshop.specification.GetAllAuthorsSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AuthorReceiverImpl implements EntityReceiver<Author> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorReceiverImpl.class);

    @Override
    public boolean addNewEntity(Author author) throws ReceiverException {
        try {
            Repository<Author> repository = new AuthorRepository();
            LOGGER.debug("trying to add track: {}", author.getName());

            if (author.getName().isEmpty()) {
                return false;
            }

            switch (repository.getStatus(author)) {
                case ACTIVE:
                    LOGGER.debug("found active author: {}", author.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted author: {}", author.getName());
                    repository.undelete(author);
                    return true;
                case NA:
                    repository.add(author);
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
            Repository<Author> repository = new AuthorRepository();
            LOGGER.debug("trying to delete track: {}", author.getName());

            if (author.getName().isEmpty()) {
                return false;
            }

            repository.delete(author);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteAuthor.\n"+e, e);
        }
    }

    @Override
    public boolean updateEntity(Author author) throws ReceiverException {
        try {
            Repository<Author> repository = new AuthorRepository();
            LOGGER.debug("trying to update author: {}", author.getName());

            if (author.getName().isEmpty()) {
                return false;
            }

            repository.update(author);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateTrack.\n"+e, e);
        }
    }

    @Override
    public List<Author> getAllEntities() throws ReceiverException {
        try {
            Repository<Author> repository = new AuthorRepository();
            Specification specification = new GetAllAuthorsSpecification();
            LOGGER.debug("Trying to get all authors.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in Get All Authors.\n" + e, e);
        }
    }

    @Override
    public List<Author> getSpecifiedEntities(String param) throws ReceiverException {
        return null;
    }

    @Override
    public List<Author> getEntitiesWithOwner(String ownerId) throws ReceiverException {
        return null;
    }
}

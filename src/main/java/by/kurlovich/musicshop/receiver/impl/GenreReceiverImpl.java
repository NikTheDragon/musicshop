package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.GenreRepository;
import by.kurlovich.musicshop.specification.GetAllGenresSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GenreReceiverImpl implements EntityReceiver<Genre> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreReceiverImpl.class);

    @Override
    public boolean addNewEntity(Genre genre) throws ReceiverException {
        try {
            Repository<Genre> repository = new GenreRepository();
            LOGGER.debug("trying to add genre: {}", genre.getName());

            if (genre.getName().isEmpty()) {
                return false;
            }

            switch (repository.getStatus(genre)) {
                case ACTIVE:
                    LOGGER.debug("found active genre: {}", genre.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted genre: {}", genre.getName());
                    repository.undelete(genre);
                    return true;
                case NA:
                    repository.add(genre);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewGenre.", e);
        }
    }

    @Override
    public boolean deleteEntity(Genre genre) throws ReceiverException {
        try {
            Repository<Genre> repository = new GenreRepository();
            LOGGER.debug("trying to delete genre: {}", genre.getName());

            if (genre.getName().isEmpty()) {
                return false;
            }

            repository.delete(genre);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteGenre.", e);
        }
    }

    @Override
    public boolean updateEntity(Genre genre) throws ReceiverException {
        try {
            Repository<Genre> repository = new GenreRepository();

            if (genre.getName().isEmpty()) {
                return false;
            }

            if (!genre.getId().isEmpty()) {
                LOGGER.debug("trying to update genre: {}", genre.getName());
                repository.update(genre);
                return true;
            }
            return false;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateGenre.", e);
        }
    }

    @Override
    public List<Genre> getAllEntities() throws ReceiverException {
        try {
            Repository<Genre> repository = new GenreRepository();
            Specification specification = new GetAllGenresSpecification();
            LOGGER.debug("trying to get all genres.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in Get All Genres.", e);
        }
    }

    @Override
    public List<Genre> getSpecifiedEntities(String param) throws ReceiverException {
        return null;
    }

    @Override
    public List<Genre> getEntitiesWithOwner(String ownerId) throws ReceiverException {
        return null;
    }
}

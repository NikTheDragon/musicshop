package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.GenreRepositoryImpl;
import by.kurlovich.musicshop.repository.specification.GetAllGenresSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class GenreReceiverImpl implements EntityReceiver<Genre> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreReceiverImpl.class);

    @Override
    public boolean addNewEntity(Genre genre) throws ReceiverException {
        try {
            EntityRepository<Genre> entityRepository = new GenreRepositoryImpl();
            LOGGER.debug("trying to add genre: {}", genre.getName());

            if (genre.getName().isEmpty()) {
                return false;
            }

            switch (entityRepository.getStatus(genre)) {
                case ACTIVE:
                    LOGGER.debug("found active genre: {}", genre.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted genre: {}", genre.getName());
                    entityRepository.undelete(genre);
                    return true;
                case NA:
                    entityRepository.add(genre);
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
            EntityRepository<Genre> entityRepository = new GenreRepositoryImpl();
            LOGGER.debug("trying to delete genre: {}", genre.getName());

            if (genre.getName().isEmpty()) {
                return false;
            }

            entityRepository.delete(genre);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteGenre.", e);
        }
    }

    @Override
    public boolean updateEntity(Genre genre) throws ReceiverException {
        try {
            EntityRepository<Genre> entityRepository = new GenreRepositoryImpl();

            if (genre.getName().isEmpty()) {
                return false;
            }

            if (!genre.getId().isEmpty()) {
                LOGGER.debug("trying to update genre: {}", genre.getName());
                entityRepository.update(genre);
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
            EntityRepository<Genre> entityRepository = new GenreRepositoryImpl();
            Specification specification = new GetAllGenresSpecification();
            LOGGER.debug("trying to get all genres.");

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in Get All Genres.", e);
        }
    }

    @Override
    public List<Genre> getSearchedEntities(SearchData searchData, String userId) throws ReceiverException {
        return Collections.emptyList();
    }

    @Override
    public List<Genre> getSpecifiedEntities(String param) throws ReceiverException {
        return Collections.emptyList();
    }
}

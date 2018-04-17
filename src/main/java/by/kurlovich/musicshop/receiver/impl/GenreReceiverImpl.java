package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.GenreReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.GenreRepository;
import by.kurlovich.musicshop.specification.GetAllGenresSpecification;
import by.kurlovich.musicshop.specification.GetGenresByNameSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GenreReceiverImpl implements GenreReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreReceiverImpl.class);

    @Override
    public boolean addNewGenre(Genre genre) throws ReceiverException {
        try {
            Repository<Genre> repository = new GenreRepository();
            Specification specification = new GetGenresByNameSpecification(genre.getName());
            LOGGER.debug("trying to add genre: {}", genre.getName());

            if (genre.getName().isEmpty()) {
                return false;
            }

            List<Genre> genresList = repository.query(specification);

            if (genresList.isEmpty()) {
                repository.add(genre);
                return true;
            }

            if (checkIfDeleted(genresList, genre)) {
                LOGGER.debug("found deleted genre: {}", genre.getName());
                repository.update(genre);
                return true;

            }
            return false;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewGenre.", e);
        }
    }

    @Override
    public boolean deleteGenre(Genre genre) throws ReceiverException {
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
    public boolean updateGenre(Genre genre) throws ReceiverException {
        try {
            if (genre.getName().isEmpty()) {
                return false;
            }

            if (!genre.getId().isEmpty()) {
                Repository<Genre> repository = new GenreRepository();
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
    public List<Genre> getAllGenres() throws ReceiverException {
        try {
            Repository<Genre> repository = new GenreRepository();
            Specification specification = new GetAllGenresSpecification();
            LOGGER.debug("trying to get all genres.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in Get All Genres.", e);
        }
    }

    private boolean checkIfDeleted(List<Genre> genresList, Genre genre) {
        for (Genre entity : genresList) {
            if (entity.getName().equals(genre.getName()) && entity.getStatus().equals("deleted")) {
                genre.setId(entity.getId());
                return true;
            }
        }
        return false;
    }
}

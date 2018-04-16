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

            List<Genre> genresList = repository.query(specification);
            if (genresList.isEmpty()) {
                repository.add(genre);
                return true;
            } else {
                return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewGenre.", e);
        }
    }

    @Override
    public List<Genre> getAllGenres() throws ReceiverException {
        try{
            Repository<Genre> repository = new GenreRepository();
            Specification specification = new GetAllGenresSpecification();
            LOGGER.debug("trying to get all genres.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in Get All Genres.", e);
        }
    }
}

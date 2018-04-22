package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.receiver.AlbumReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.AlbumRepository;
import by.kurlovich.musicshop.specification.GetAllAlbumsSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AlbumReceiverImpl implements AlbumReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumReceiverImpl.class);

    @Override
    public boolean addNewAuthor(Album album) throws ReceiverException {
        try {
            Repository<Album> repository = new AlbumRepository();
            LOGGER.debug("trying to add album: {}", album.getName());

            if (album.getName().isEmpty()) {
                return false;
            }

            switch (repository.checkStatus(album)) {
                case ACTIVE:
                    LOGGER.debug("found active album: {}", album.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted album: {}", album.getName());
                    repository.setStatus(album);
                    return true;
                case NA:
                    repository.add(album);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewAlbum.\n" + e, e);
        }
    }

    @Override
    public boolean deleteAuthor(Album album) throws ReceiverException {
        try {
            Repository<Album> repository = new AlbumRepository();
            LOGGER.debug("trying to delete album: {}", album.getName());

            if (album.getName().isEmpty()) {
                return false;
            }

            repository.delete(album);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteAlbum.\n" + e, e);
        }
    }

    @Override
    public boolean updateAuthor(Album album) throws ReceiverException {
        try {
            Repository<Album> repository = new AlbumRepository();
            LOGGER.debug("trying to update album: {}", album.getName());

            if (album.getName().isEmpty()) {
                return false;
            }

            repository.update(album);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateAlbum.\n" + e, e);
        }
    }

    @Override
    public List<Album> getAllAlbums() throws ReceiverException {
        try {
            Repository<Album> repository = new AlbumRepository();
            Specification specification = new GetAllAlbumsSpecification();
            LOGGER.debug("Trying to get all albums.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllAlbums.\n" + e, e);
        }
    }
}

package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.AlbumRepositoryImpl;
import by.kurlovich.musicshop.repository.specification.GetAlbumByIdSpecification;
import by.kurlovich.musicshop.repository.specification.GetAllAlbumsSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class AlbumReceiverImpl implements EntityReceiver<Album> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumReceiverImpl.class);

    @Override
    public boolean addNewEntity(Album album) throws ReceiverException {
        try {
            EntityRepository<Album> entityRepository = new AlbumRepositoryImpl();
            LOGGER.debug("trying to add album: {}", album.getName());

            if (album.getName().isEmpty()) {
                return false;
            }

            switch (entityRepository.getStatus(album)) {
                case ACTIVE:
                    LOGGER.debug("found active album: {}", album.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted album: {}", album.getName());
                    entityRepository.undelete(album);
                    return true;
                case NA:
                    entityRepository.add(album);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewEntity of AlbumReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public boolean deleteEntity(Album album) throws ReceiverException {
        try {
            EntityRepository<Album> entityRepository = new AlbumRepositoryImpl();
            LOGGER.debug("trying to delete album: {}", album.getName());

            if (album.getName().isEmpty()) {
                return false;
            }

            entityRepository.delete(album);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteEntity of AlbumReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public boolean updateEntity(Album album) throws ReceiverException {
        try {
            EntityRepository<Album> entityRepository = new AlbumRepositoryImpl();
            LOGGER.debug("trying to update album: {}", album.getName());

            if (album.getName().isEmpty()) {
                return false;
            }

            entityRepository.update(album);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateEntity of AlbumReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Album> getAllEntities() throws ReceiverException {
        try {
            EntityRepository<Album> entityRepository = new AlbumRepositoryImpl();
            Specification specification = new GetAllAlbumsSpecification();
            LOGGER.debug("Trying to get all albums.");

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllEntities of AlbumReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Album> getSearchedEntities(SearchData searchData, String userId) throws ReceiverException {
        return Collections.emptyList();
    }

    @Override
    public List<Album> getSpecifiedEntities(String param) throws ReceiverException {
        try {
            EntityRepository<Album> entityRepository = new AlbumRepositoryImpl();
            Specification specification = new GetAlbumByIdSpecification(param);
            LOGGER.debug("trying to get specified albums with id {}.", param);

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedEntities of AlbumReceiverImpl.\n" + e, e);
        }
    }
}

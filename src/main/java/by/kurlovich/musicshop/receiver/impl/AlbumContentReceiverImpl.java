package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.AlbumContentRepository;
import by.kurlovich.musicshop.specification.GetAlbumContentByAlbumIdSpecification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class AlbumContentReceiverImpl implements EntityReceiver<Content> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlbumContentReceiverImpl.class);

    @Override
    public boolean addNewEntity(Content item) throws ReceiverException {
        try {
            Repository<Content> repository = new AlbumContentRepository();

            if (item.getTrackName().isEmpty()) {
                return false;
            }

            switch (repository.getStatus(item)) {
                case ACTIVE:
                    LOGGER.debug("found active track: {}, for album.", item.getTrackName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted track:{}, for album.", item.getTrackName());
                    repository.undelete(item);
                    return true;
                case NA:
                    repository.add(item);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewEntity of AlbumContentReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public boolean deleteEntity(Content item) throws ReceiverException {
        try {
            Repository<Content> repository = new AlbumContentRepository();
            LOGGER.debug("trying to delete content from album.");

            if (item.getTrackId().isEmpty()) {
                return false;
            }

            repository.delete(item);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteEntity of AlbumContentReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public boolean updateEntity(Content item) throws ReceiverException {
        return false;
    }

    @Override
    public List<Content> getAllEntities() throws ReceiverException {
        return Collections.emptyList();
    }

    @Override
    public List<Content> getSpecifiedEntities(String param) throws ReceiverException {
        try {
            Repository<Content> repository = new AlbumContentRepository();
            Specification specification = new GetAlbumContentByAlbumIdSpecification(param);
            LOGGER.debug("trying to get specified content for album with id {}.", param);

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedEntities of AlbumContentReceiverImpl.\n" + e, e);
        }
    }
}

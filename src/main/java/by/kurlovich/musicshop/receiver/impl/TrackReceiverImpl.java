package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.TrackRepositoryImpl;
import by.kurlovich.musicshop.repository.specification.GetAllTracksSpecification;
import by.kurlovich.musicshop.repository.specification.GetTracksByAuthorNameSpecification;
import by.kurlovich.musicshop.repository.specification.SearchTracksWithOwnerSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TrackReceiverImpl implements EntityReceiver<Track> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackReceiverImpl.class);

    @Override
    public boolean addNewEntity(Track track) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            LOGGER.debug("trying to add track: {}", track.getName());

            if (track.getName().isEmpty()) {
                return false;
            }

            switch (entityRepository.getStatus(track)) {
                case ACTIVE:
                    LOGGER.debug("found active track: {}", track.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted track: {}", track.getName());
                    entityRepository.undelete(track);
                    return true;
                case NA:
                    entityRepository.add(track);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewTrack.", e);
        }
    }

    @Override
    public boolean deleteEntity(Track track) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            LOGGER.debug("trying to delete track: {}", track.getName());

            if (track.getName().isEmpty()) {
                return false;
            }

            entityRepository.delete(track);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteTrack.\n" + e, e);
        }
    }

    @Override
    public boolean updateEntity(Track track) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            LOGGER.debug("trying to update track: {}", track.getName());

            if (track.getName().isEmpty()) {
                return false;
            }

            entityRepository.update(track);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateTrack.\n" + e, e);
        }
    }

    @Override
    public List<Track> getAllEntities() throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            Specification specification = new GetAllTracksSpecification();
            LOGGER.debug("trying to getId all tracks.");

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllEntities of TrackReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getSearchedEntities(SearchData searchData, String userId) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            Specification specification = new SearchTracksWithOwnerSpecification(searchData, userId);
            LOGGER.debug("trying to search tracks.");

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSearchedEntities of TrackReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getSpecifiedEntities(String param) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            Specification specification = new GetTracksByAuthorNameSpecification(param);
            LOGGER.debug("trying to getId specified author: {}, tracks.", param);

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedEntities of TrackReceiverImpl.\n" + e, e);
        }
    }
}

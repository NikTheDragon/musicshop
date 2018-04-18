package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.TrackReceiver;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.TrackRepository;
import by.kurlovich.musicshop.specification.GetAllTracksSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TrackReceiverImpl implements TrackReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrackReceiverImpl.class);

    @Override
    public boolean addNewTrack(Track track) throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            LOGGER.debug("trying to add track: {}", track.getName());

            if (track.getName().isEmpty()) {
                return false;
            }

            switch (repository.checkStatus(track)) {
                case ACTIVE:
                    LOGGER.debug("found active track: {}", track.getName());
                    return false;
                case DELETE:
                    LOGGER.debug("found deleted track: {}", track.getName());
                    repository.setStatus(track);
                    return true;
                case NA:
                    repository.add(track);
                    return true;
                default:
                    return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewTrack.", e);
        }
    }

    @Override
    public boolean deleteTrack(Track track) throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            LOGGER.debug("trying to delete track: {}", track.getName());

            if (track.getName().isEmpty()) {
                return false;
            }

            repository.delete(track);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteTrack.\n"+e, e);
        }
    }

    @Override
    public boolean updateTrack(Track track) throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            LOGGER.debug("trying to update track: {}", track.getName());

            if (track.getName().isEmpty()) {
                return false;
            }

            repository.update(track);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateTrack.\n"+e, e);
        }
    }

    @Override
    public List<Track> getAllTracks() throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            Specification specification = new GetAllTracksSpecification();
            LOGGER.debug("trying to get all tracks.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in Get All Tracks.\n"+e, e);
        }
    }
}

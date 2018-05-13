package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.AlbumRepository;
import by.kurlovich.musicshop.repository.impl.MixRepository;
import by.kurlovich.musicshop.repository.impl.TrackRepository;
import by.kurlovich.musicshop.repository.impl.UserRepository;
import by.kurlovich.musicshop.specification.*;
import by.kurlovich.musicshop.validator.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReceiverImpl.class);

    @Override
    public List<User> getAllUsers() throws ReceiverException {
        try {
            Repository<User> repository = new UserRepository();
            Specification specification = new GetAllUsersSpecification();
            LOGGER.debug("trying to get all users.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllUsers of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getAllTracksWithOwner(String userId) throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            Specification specification = new GetAllTracksWithOwnerIdSpecification(userId);
            LOGGER.debug("trying to get tracks with owners id:{}.", userId);

            return repository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllTracksWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getUserOwnedTracks(String userId) throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            Specification specification = new GetUserOwnedTracksSpecification(userId);
            LOGGER.debug("trying to get user id:{} owned tracks", userId);

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getUserOwnedTracks of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getMixTracksWithOwner(String userId, String mixId) throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            Specification specification = new GetMixContentWithOwnersIdByMixIdSpecification(userId, mixId);
            LOGGER.debug("Trying to get all tracks for mix:{}, and owner:{}.", mixId, userId);

            return repository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllMixesWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getAlbumTracksWithOwner(String userId, String albumId) throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            Specification specification = new GetAlbumContentWithOwnersIdByMixIdSpecification(userId, albumId);
            LOGGER.debug("Trying to get all tracks for album:{}, and owner:{}.", albumId, userId);

            return repository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAlbumTracksWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getAllMixesWithOwner(String userId) throws ReceiverException {
        try {
            Repository<Mix> repository = new MixRepository();
            Specification specification = new GetAllMixesWithOwnerIdSpecification(userId);
            LOGGER.debug("Trying to get all mixes with owner.");

            return repository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllMixesWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getUserOwnedMixes(String userId) throws ReceiverException {
        try {
            Repository<Mix> repository = new MixRepository();
            Specification specification = new GetUserOwnedMixesSpecification(userId);
            LOGGER.debug("trying to get user id:{} owned mixes.", userId);

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getUserOwnedMixes of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Album> getAllAlbumsWithOwner(String userId) throws ReceiverException {
        try {
            Repository<Album> repository = new AlbumRepository();
            Specification specification = new GetAllAlbumsWithOwnerIdSpecification(userId);
            LOGGER.debug("Trying to get all albums with owner.");

            return repository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllAlbumsWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Album> getUserOwnedAlbums(String userId) throws ReceiverException {
        try {
            Repository<Album> repository = new AlbumRepository();
            Specification specification = new GetUserOwnedAlbumsSpecification(userId);
            LOGGER.debug("trying to get user id:{} owned albums.", userId);

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getUserOwnedAlbums of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public Map<String, String> validateUser(User user) {
        Map<String, String> messageMap = new HashMap<>();
        return messageMap;
    }

    @Override
    public boolean addNewUser(User user) throws ReceiverException {
        try {
            Repository<User> repository = new UserRepository();
            Specification specification = new GetUserByLoginSpecification(user.getLogin());
            LOGGER.debug("trying to add user: {}", user);

            List<User> usersList = repository.query(specification);
            if (usersList.isEmpty()) {
                repository.add(user);
                return true;
            } else {
                return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewUser.\n" + e, e);
        }
    }

    @Override
    public boolean updateUser(User user) throws ReceiverException {
        try {
            Repository<User> repository = new UserRepository();
            LOGGER.debug("trying to update user: {}", user.getName());

            if (user.getName().isEmpty()) {
                return false;
            }

            repository.update(user);
            return true;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateUser of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public User loginUser(String login, String password) throws ReceiverException {
        try {
            Repository<User> repository = new UserRepository();
            Specification specification = new GetUserByLoginPasswordSpecification(login, password);
            LOGGER.debug("trying to log user.");

            List<User> usersList = repository.query(specification);

            if (!usersList.isEmpty()) {
                return usersList.get(0);
            } else {
                return null;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in loginUser.\n" + e, e);
        }
    }

    @Override
    public void buyTrack(String userId, String trackId) throws ReceiverException {
        try {
            Repository<Track> repository = new TrackRepository();
            Specification specification = new BuyTrackSpecification(userId, trackId);
            LOGGER.debug("user:{}, trying to buy track:{}.", userId, trackId);

            repository.buy(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in buyTrack of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public void buyAlbum(String userId, String albumId) throws ReceiverException {
        try {
            Repository<Album> repository = new AlbumRepository();
            Specification specification = new BuyAlbumSpecification(userId, albumId);
            LOGGER.debug("user:{}, trying to buy album:{}.", userId, albumId);

            repository.buy(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in buyAlbum of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public void buyMix(String userId, String mixId) throws ReceiverException {
        try {
            Repository<Mix> repository = new MixRepository();
            Specification specification = new BuyMixSpecification(userId, mixId);
            LOGGER.debug("user:{}, trying to buy mix:{}.", userId, mixId);

            repository.buy(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in buyMix of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<User> getSpecifiedUsers(String userId) throws ReceiverException {
        try {
            Repository<User> repository = new UserRepository();
            Specification specification = new GetUserByIdSpecification(userId);
            LOGGER.debug("trying to get specified users.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedUsers of UserReceiverImpl.\n" + e, e);
        }
    }
}

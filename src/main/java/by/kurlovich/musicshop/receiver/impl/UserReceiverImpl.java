package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.EntityRepository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.UserRepository;
import by.kurlovich.musicshop.repository.impl.AlbumRepositoryImpl;
import by.kurlovich.musicshop.repository.impl.MixRepositoryImpl;
import by.kurlovich.musicshop.repository.impl.TrackRepositoryImpl;
import by.kurlovich.musicshop.repository.impl.UserRepositoryImpl;
import by.kurlovich.musicshop.repository.specification.*;

import by.kurlovich.musicshop.util.PasswordSHAGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReceiverImpl.class);

    @Override
    public List<User> getAllUsers() throws ReceiverException {
        try {
            UserRepository userRepository = new UserRepositoryImpl();
            Specification specification = new GetAllUsersSpecification();
            LOGGER.debug("trying to get all users.");

            return userRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllUsers of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getAllTracksWithOwner(String userId) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            Specification specification = new GetAllTracksWithOwnerIdSpecification(userId);
            LOGGER.debug("trying to get tracks with owners id:{}.", userId);

            return entityRepository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllTracksWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getUserOwnedTracks(String userId) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            Specification specification = new GetUserOwnedTracksSpecification(userId);
            LOGGER.debug("trying to get user id:{} owned tracks", userId);

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getUserOwnedTracks of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getMixTracksWithOwner(String userId, String mixId) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            Specification specification = new GetMixContentWithOwnersIdByMixIdSpecification(userId, mixId);
            LOGGER.debug("Trying to get all tracks for mix:{}, and owner:{}.", mixId, userId);

            return entityRepository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllMixesWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Track> getAlbumTracksWithOwner(String userId, String albumId) throws ReceiverException {
        try {
            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            Specification specification = new GetAlbumContentWithOwnersIdByMixIdSpecification(userId, albumId);
            LOGGER.debug("Trying to get all tracks for album:{}, and owner:{}.", albumId, userId);

            return entityRepository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAlbumTracksWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getAllMixesWithOwner(String userId) throws ReceiverException {
        try {
            EntityRepository<Mix> entityRepository = new MixRepositoryImpl();
            Specification specification = new GetAllMixesWithOwnerIdSpecification(userId);
            LOGGER.debug("Trying to get all mixes with owner.");

            return entityRepository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllMixesWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Mix> getUserOwnedMixes(String userId) throws ReceiverException {
        try {
            EntityRepository<Mix> entityRepository = new MixRepositoryImpl();
            Specification specification = new GetUserOwnedMixesSpecification(userId);
            LOGGER.debug("trying to get user id:{} owned mixes.", userId);

            return entityRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getUserOwnedMixes of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Album> getAllAlbumsWithOwner(String userId) throws ReceiverException {
        try {
            EntityRepository<Album> entityRepository = new AlbumRepositoryImpl();
            Specification specification = new GetAllAlbumsWithOwnerIdSpecification(userId);
            LOGGER.debug("Trying to get all albums with owner.");

            return entityRepository.queryWithOwners(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllAlbumsWithOwner of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<Album> getUserOwnedAlbums(String userId) throws ReceiverException {
        try {
            EntityRepository<Album> entityRepository = new AlbumRepositoryImpl();
            Specification specification = new GetUserOwnedAlbumsSpecification(userId);
            LOGGER.debug("trying to get user id:{} owned albums.", userId);

            return entityRepository.query(specification);

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
            UserRepository userRepository = new UserRepositoryImpl();
            Specification specification = new GetUserByLoginSpecification(user.getLogin());
            LOGGER.debug("trying to add user: {}", user);

            List<User> usersList = userRepository.query(specification);
            if (usersList.isEmpty()) {
                user.setPassword(PasswordSHAGenerator.getPassword(user.getPassword()));

                userRepository.add(user);
                return true;
            } else {
                return false;
            }

        } catch (RepositoryException | NoSuchAlgorithmException e) {
            throw new ReceiverException("Exception in addNewUser.\n" + e, e);
        }
    }

    @Override
    public boolean updateUser(User user) throws ReceiverException {
        try {
            UserRepository userRepository = new UserRepositoryImpl();
            Specification specification = new GetUserByLoginSpecification(user.getLogin());
            LOGGER.debug("trying to update user: {}", user.getName());

            List<User> usersList = userRepository.query(specification);
            if (usersList.isEmpty()) {
                userRepository.update(user);
                return true;
            }

            if (!usersList.isEmpty()) {
                User newUser = usersList.get(0);
                if (user.getId().equals(newUser.getId())) {
                    userRepository.update(user);
                    return true;
                }
            }
            return false;

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateUser of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public boolean updatePassword(String oldPassword, String newPassword, User sessionUser) throws ReceiverException {
        try {
            LOGGER.debug("session pass: {}", sessionUser.getPassword());
            if (!PasswordSHAGenerator.getPassword(oldPassword).equals(sessionUser.getPassword())) {
                return false;
            }

            UserRepository userRepository = new UserRepositoryImpl();
            newPassword = PasswordSHAGenerator.getPassword(newPassword);
            LOGGER.debug("trying to update user password.");

            userRepository.updatePassword(newPassword, sessionUser.getId());

            return true;
        } catch (RepositoryException | NoSuchAlgorithmException e) {
            throw new ReceiverException("Exception in updatePassword of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public User loginUser(String login, String password) throws ReceiverException {
        try {
            UserRepository userRepository = new UserRepositoryImpl();
            password = PasswordSHAGenerator.getPassword(password);

            Specification specification = new GetUserByLoginPasswordSpecification(login, password);
            LOGGER.debug("trying to log user.");

            List<User> usersList = userRepository.query(specification);

            if (!usersList.isEmpty()) {
                return usersList.get(0);
            } else {
                return new User();
            }

        } catch (RepositoryException | NoSuchAlgorithmException e) {
            throw new ReceiverException("Exception in loginUser.\n" + e, e);
        }
    }

    @Override
    public String buyTrack(User currentUser, String trackId, int trackPrice) throws ReceiverException {
        Specification specification;
        try {
            LOGGER.debug("User: {}, trying to buy track: {}.", currentUser.getId(), trackId);

            EntityRepository<Track> entityRepository = new TrackRepositoryImpl();
            UserRepository userRepository = new UserRepositoryImpl();

            specification = new GetTracksByIdSpecification(trackId);
            List<Track> specifiedTrack = entityRepository.query(specification);

            if (trackPrice > currentUser.getPoints()) {
                return "insufficient points.";
            }

            if (specifiedTrack.get(0).getStatus().equals("active")) {
                specification = new BuyTrackSpecification(currentUser.getId(), trackId);
                entityRepository.buy(specification);

                int userPoints = currentUser.getPoints();
                userPoints -= trackPrice;
                currentUser.setPoints(userPoints);

                userRepository.update(currentUser);

                return "true";
            }

            return "can't find track to buy.";

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in buyTrack of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public String buyAlbum(User currentUser, String albumId, int albumPrice) throws ReceiverException {
        Specification specification;
        try {
            LOGGER.debug("User: {}, trying to buy album: {}.", currentUser.getId(), albumId);

            EntityRepository<Album> entityRepository = new AlbumRepositoryImpl();
            UserRepository userRepository = new UserRepositoryImpl();

            specification = new GetAlbumByIdSpecification(albumId);
            List<Album> specifiedAlbum = entityRepository.query(specification);

            if (albumPrice > currentUser.getPoints()) {
                return "insufficient points.";
            }

            if (specifiedAlbum.get(0).getStatus().equals("active")) {
                specification = new BuyAlbumSpecification(currentUser.getId(), albumId);
                entityRepository.buy(specification);

                int userPoints = currentUser.getPoints();
                userPoints -= albumPrice;
                currentUser.setPoints(userPoints);

                userRepository.update(currentUser);

                return "true";
            }

            return "can't find album to buy.";

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in buyAlbum of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public String buyMix(User currentUser, String mixId, int mixPrice) throws ReceiverException {
        Specification specification;
        try {
            LOGGER.debug("User: {}, trying to buy mix: {}.", currentUser.getId(), mixId);

            EntityRepository<Mix> entityRepository = new MixRepositoryImpl();
            UserRepository userRepository = new UserRepositoryImpl();

            specification = new GetMixByIdSpecification(mixId);
            List<Mix> specifiedMix = entityRepository.query(specification);

            if (mixPrice > currentUser.getPoints()) {
                return "insufficient points.";
            }

            System.out.println("status="+specifiedMix.get(0).getStatus()+".");

            if (specifiedMix.get(0).getStatus().equals("active")) {
                specification = new BuyMixSpecification(currentUser.getId(), mixId);
                entityRepository.buy(specification);

                int userPoints = currentUser.getPoints();
                userPoints -= mixPrice;
                currentUser.setPoints(userPoints);

                userRepository.update(currentUser);

                return "true";
            }

            return "can't find mix to buy.";

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in buyMix of UserReceiverImpl.\n" + e, e);
        }
    }

    @Override
    public List<User> getSpecifiedUsers(String userId) throws ReceiverException {
        try {
            UserRepository userRepository = new UserRepositoryImpl();
            Specification specification = new GetUserByIdSpecification(userId);
            LOGGER.debug("trying to get specified users.");

            return userRepository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedUsers of UserReceiverImpl.\n" + e, e);
        }
    }
}

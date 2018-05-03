package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
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
    private ObjectValidator objectValidator = new ObjectValidator();

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
    public Map<String, String> validateUser(User user) {
        Map<String, String> messageMap = new HashMap<>();

        if (!objectValidator.validateUser(messageMap, user)) {
            messageMap.put("validate", "false");
        } else {
            messageMap.put("validate", "true");
        }

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
            LOGGER.debug("trying to get specified users.");

            repository.buy(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getSpecifiedUsers of UserReceiverImpl.\n" + e, e);
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

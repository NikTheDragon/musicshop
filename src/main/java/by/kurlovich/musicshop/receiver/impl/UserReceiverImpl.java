package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.UserRepository;
import by.kurlovich.musicshop.specification.GetAllUsersSpecification;
import by.kurlovich.musicshop.specification.GetUserByLoginPasswordSpecification;
import by.kurlovich.musicshop.specification.GetUserByLoginSpecification;
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

    public Map<String, String> validateUser(User user) {
        Map<String, String> messageMap = new HashMap<>();

        if (!objectValidator.validateUser(messageMap, user)) {
            messageMap.put("validate", "false");
        } else {
            messageMap.put("validate", "true");
        }

        return messageMap;
    }

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
            throw new ReceiverException("Exception in addNewUser", e);
        }
    }

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
            throw new ReceiverException("Exception in addNewUser", e);
        }
    }

    public List<User> getAllEntities() throws ReceiverException {
        try {
            Repository<User> repository = new UserRepository();
            Specification specification = new GetAllUsersSpecification();
            LOGGER.debug("trying to get all users.");

            return repository.query(specification);

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllEntities of UserReceiverImpl.\n" + e, e);
        }
    }
}

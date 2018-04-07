package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.UserRepository;
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

    public UserReceiverImpl() {

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
                user.setRole("user");
                user.setStatus("active");
                repository.add(user);
                return true;
            } else {
                return false;
            }

        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewUser", e);
        }
    }
}

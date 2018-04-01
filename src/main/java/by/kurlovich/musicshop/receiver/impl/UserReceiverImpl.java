package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.Specification;
import by.kurlovich.musicshop.repository.impl.UserRepository;
import by.kurlovich.musicshop.specification.GetUserByLoginSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReceiverImpl.class);
    private static final UserReceiverImpl instance = new UserReceiverImpl();

    public static UserReceiverImpl getInstance() {
        return instance;
    }

    public boolean addNewUser(User user) throws ReceiverException {
        Repository<User> repository = new UserRepository();
        Specification specification = new GetUserByLoginSpecification(user.getLogin());
        LOGGER.debug("trying to add user: {}", user);

        try {
            List<User> usersList = repository.query(specification);
            if (usersList.size() == 0) {
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

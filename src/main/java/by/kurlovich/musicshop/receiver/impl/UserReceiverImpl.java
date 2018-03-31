package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.repository.IsExistsSpecification;
import by.kurlovich.musicshop.repository.IsLoginExistsSpecification;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.RepositoryException;
import by.kurlovich.musicshop.repository.impl.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserReceiverImpl implements UserReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReceiverImpl.class);
    private static final UserReceiverImpl instance = new UserReceiverImpl();

    public static UserReceiverImpl getInstance() {
        return instance;
    }

    public boolean addNewUser(User user) throws ReceiverException {
        Repository<User> repository = new UserRepository();
        IsExistsSpecification loginExistsSpecification = new IsLoginExistsSpecification(user.getLogin());
        LOGGER.debug("trying to add user: {}", user);

        try {
            boolean isExists = repository.isExists(loginExistsSpecification);
            return repository.add(user);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewUser", e);
        }
    }
}

package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.exception.ReceiverException;
import by.kurlovich.musicshop.repository.Repository;
import by.kurlovich.musicshop.repository.exception.RepositoryException;
import by.kurlovich.musicshop.repository.impl.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserReceiver.class);
    private static final UserReceiver instance = new UserReceiver();
    private Repository<User> repository = new UserRepository();

    public static UserReceiver getInstance() {
        return instance;
    }

    public boolean addNewUser(User user) throws ReceiverException {

        LOGGER.debug("trying to add user: {}", user);

        try {
            return repository.add(user);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in addNewUser", e);
        }
    }
}

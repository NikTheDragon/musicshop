package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.User;

public interface UserReceiver {
    boolean addNewUser(User user) throws ReceiverException;
}

package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.User;

import java.util.Map;

public interface UserReceiver {

     Map<String, String> validateUser(User user) throws ReceiverException;
     boolean addNewUser(User user) throws ReceiverException;
}

package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.User;

import java.util.List;
import java.util.Map;

public interface UserReceiver {
     List<User> getAllUsers() throws ReceiverException;
     List<User> getSpecifiedUsers(String userId) throws ReceiverException;
     Map<String, String> validateUser(User user) throws ReceiverException;
     boolean addNewUser(User user) throws ReceiverException;
     boolean updateUser(User user) throws ReceiverException;
     User loginUser(String login, String password) throws ReceiverException;

}

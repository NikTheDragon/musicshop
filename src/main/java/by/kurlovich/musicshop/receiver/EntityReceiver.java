package by.kurlovich.musicshop.receiver;

import java.util.List;

public interface EntityReceiver<T> {
    boolean addNewEntity(T item) throws ReceiverException;
    boolean deleteEntity(T item) throws ReceiverException;
    boolean updateEntity(T item) throws ReceiverException;
    List<T> getAllEntities() throws ReceiverException;
    List<T> getSpecifiedEntities(String param) throws ReceiverException;
}

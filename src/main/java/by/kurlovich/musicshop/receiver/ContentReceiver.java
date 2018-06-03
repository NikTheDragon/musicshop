package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.Content;

import java.util.List;

public interface ContentReceiver {
    boolean addNewEntity(Content item) throws ReceiverException;
    boolean deleteEntity(Content item) throws ReceiverException;
    List<Content> getSpecifiedEntities(String param) throws ReceiverException;
}

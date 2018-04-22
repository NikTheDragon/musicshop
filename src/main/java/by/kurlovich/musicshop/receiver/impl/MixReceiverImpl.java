package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import java.util.List;

public class MixReceiverImpl implements EntityReceiver<Mix> {

    @Override
    public boolean addNewEntity(Mix item) throws ReceiverException {
        return false;
    }

    @Override
    public boolean deleteEntity(Mix item) throws ReceiverException {
        return false;
    }

    @Override
    public boolean updateEntity(Mix item) throws ReceiverException {
        return false;
    }

    @Override
    public List<Mix> getAllEntities() throws ReceiverException {
        return null;
    }
}

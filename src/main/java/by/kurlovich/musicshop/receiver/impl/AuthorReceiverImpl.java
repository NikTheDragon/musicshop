package by.kurlovich.musicshop.receiver.impl;

import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.receiver.AuthorReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import java.util.List;

public class AuthorReceiverImpl implements AuthorReceiver {

    @Override
    public boolean addNewTrack(Author author) throws ReceiverException {
        return false;
    }

    @Override
    public boolean deleteTrack(Author author) throws ReceiverException {
        return false;
    }

    @Override
    public boolean updateTrack(Author author) throws ReceiverException {
        return false;
    }

    @Override
    public List<Author> getAllAuthors() throws ReceiverException {
        return null;
    }
}

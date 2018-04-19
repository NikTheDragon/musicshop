package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.Author;

import java.util.List;

public interface AuthorReceiver {
    boolean addNewTrack(Author author) throws ReceiverException;
    boolean deleteTrack(Author author) throws ReceiverException;
    boolean updateTrack(Author author) throws ReceiverException;
    List<Author> getAllAuthors() throws ReceiverException;
}

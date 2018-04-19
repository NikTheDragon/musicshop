package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.Author;

import java.util.List;

public interface AuthorReceiver {
    boolean addNewAuthor(Author author) throws ReceiverException;
    boolean deleteAuthor(Author author) throws ReceiverException;
    boolean updateAuthor(Author author) throws ReceiverException;
    List<Author> getAllAuthors() throws ReceiverException;
}

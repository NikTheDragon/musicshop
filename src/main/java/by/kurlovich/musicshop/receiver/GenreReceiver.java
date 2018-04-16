package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.Genre;

import java.util.List;

public interface GenreReceiver {
    boolean addNewGenre(Genre genre) throws ReceiverException;
    boolean deleteGenre(Genre genre) throws ReceiverException;
    List<Genre> getAllGenres() throws ReceiverException;
}

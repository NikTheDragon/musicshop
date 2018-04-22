package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.Album;

import java.util.List;

public interface AlbumReceiver {
    boolean addNewAuthor(Album album) throws ReceiverException;
    boolean deleteAuthor(Album album) throws ReceiverException;
    boolean updateAuthor(Album album) throws ReceiverException;
    List<Album> getAllAlbums() throws ReceiverException;
}

package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;

import java.util.List;
import java.util.Map;

public interface UserReceiver {
    List<User> getAllUsers() throws ReceiverException;
    List<User> getSpecifiedUsers(String userId) throws ReceiverException;
    List<Track> getAllTracksWithOwner(String userId) throws ReceiverException;
    List<Track> getUserOwnedTracks(String userId) throws ReceiverException;
    List<Track> getMixTracksWithOwner (String userId, String mixId) throws ReceiverException;
    List<Track> getAlbumTracksWithOwner (String userId, String albumId) throws ReceiverException;
    List<Mix> getAllMixesWithOwner(String userId) throws ReceiverException;
    List<Mix> getUserOwnedMixes(String userId) throws ReceiverException;
    List<Album> getAllAlbumsWithOwner(String userId) throws ReceiverException;
    List<Album> getUserOwnedAlbums(String userId) throws ReceiverException;
    Map<String, String> validateUser(User user) throws ReceiverException;
    boolean addNewUser(User user) throws ReceiverException;
    boolean updateUser(User user) throws ReceiverException;
    boolean updatePassword(String oldPassword, String newPassword, User sessionUser) throws ReceiverException;
    User loginUser(String login, String password) throws ReceiverException;
    String buyTrack(User currentUser, String trackId, int trackPrice) throws ReceiverException;
    String buyAlbum(User currentUser, String albumId, int albumPrice) throws ReceiverException;
    String buyMix(User currentUser, String mixId, int mixPrice) throws ReceiverException;

}

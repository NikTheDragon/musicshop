package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.entity.Track;

import java.util.List;

public interface TrackReceiver {
    boolean addNewTrack(Track track) throws ReceiverException;
    boolean deleteTrack(Track track) throws ReceiverException;
    boolean updateTrack(Track track) throws ReceiverException;
    List<Track> getAllTracks() throws ReceiverException;
}

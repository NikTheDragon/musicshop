package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class BuyTrackSpecification implements SqlSpecification {
    private String userId;
    private String trackId;

    public BuyTrackSpecification (String userId, String trackId) {
        this.userId = userId;
        this.trackId = trackId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("INSERT INTO purchased_tracks (user_id, track_id) VALUES ('%1$s','%2$s')", userId, trackId);
    }
}

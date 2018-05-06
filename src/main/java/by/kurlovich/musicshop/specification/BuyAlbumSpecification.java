package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class BuyAlbumSpecification implements SqlSpecification {
    private String userId;
    private String albumId;

    public BuyAlbumSpecification (String userId, String albumId) {
        this.userId = userId;
        this.albumId = albumId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("INSERT INTO purchased_albums (user_id, album_id) VALUES ('%1$s','%2$s')", userId, albumId);
    }
}

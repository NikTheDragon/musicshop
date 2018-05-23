package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllAlbumsWithOwnerIdSpecification implements SqlSpecification {
    private String userId;

    public GetAllAlbumsWithOwnerIdSpecification (String userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT a.id, a.name, ar.name AS author, g.name AS genre, a.year, (SELECT COUNT(track_id) FROM albums_content ac WHERE a.id = ac.album_id AND ac.status='active' AND a.status='active') AS tracks, a.status, pa.user_id AS owner FROM albums a LEFT OUTER JOIN purchased_albums pa ON pa.album_id = a.id AND pa.user_id='%1$s', authors ar, genres g WHERE a.genre=g.id AND a.author=ar.id", userId);
    }
}

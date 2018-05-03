package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetTracksWithOwnerIdSpecification implements SqlSpecification {
    String userId;

    public GetTracksWithOwnerIdSpecification(String userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT t.id, t.name, a.name AS author, g.name AS genre, t.year, t.length, t.status, pt.user_id AS owner FROM tracks t LEFT OUTER JOIN purchased_tracks pt ON pt.track_id = t.id AND pt.user_id='%1$s', genres g, authors a WHERE t.genre=g.id AND t.author=a.id", userId);
    }
}

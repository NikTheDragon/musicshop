package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetUserOwnedTracksSpecification implements SqlSpecification {
    private String userId;
    private String sql = "SELECT t.id, t.name, a.name AS author, g.name AS genre, t.year, t.length, t.status FROM tracks t, genres g, authors a, purchased_tracks pt WHERE t.genre=g.id AND t.author=a.id AND pt.track_id=t.id AND pt.user_id='%1$s'";

    public GetUserOwnedTracksSpecification(String userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return String.format(sql, userId);
    }
}

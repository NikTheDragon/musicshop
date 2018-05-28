package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetTracksByIdSpecification implements SqlSpecification {
    private static String sql = "SELECT t.id, t.name, a.name AS author, g.name AS genre, t.year, t.length, t.status FROM tracks t, genres g, authors a WHERE t.genre=g.id AND t.author=a.id AND t.id='%1$s'";
    private String trackId;

    public GetTracksByIdSpecification(String trackId) {
        this.trackId = trackId;
    }

    @Override
    public String toSqlQuery() {
        return String.format(sql, trackId);
    }
}

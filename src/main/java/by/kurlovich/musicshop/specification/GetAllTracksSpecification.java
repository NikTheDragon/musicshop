package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllTracksSpecification implements SqlSpecification {
    private static String sql = "SELECT t.id, t.name, a.name AS author, g.name AS genre, t.year, t.length, t.status FROM tracks t, genres g, authors a WHERE t.genre=g.id AND t.author=a.id";

    @Override
    public String toSqlQuery() {

        return String.format(sql);
    }
}

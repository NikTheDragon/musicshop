package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetTracksByParamSpecification implements SqlSpecification {
    String param;

    public GetTracksByParamSpecification (String param) {
        this.param = param;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT t.id, t.name, a.name AS author, g.name AS genre, t.year, t.length, t.status FROM tracks t, genres g, authors a WHERE t.genre=g.id AND t.author=a.id AND a.name='%1$s'", param);
    }
}

package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAlbumsByParamSpecification implements SqlSpecification {
    String param;

    public GetAlbumsByParamSpecification(String param) {
        this.param = param;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT al.id, al.name, a.name AS author, g.name AS genre, al.year, al.status FROM authors a, genres g, albums al WHERE al.genre=g.id AND al.author=a.id AND al.id='%1$s'", param);
    }
}

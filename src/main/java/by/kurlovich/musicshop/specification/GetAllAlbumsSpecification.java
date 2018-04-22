package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllAlbumsSpecification implements SqlSpecification {

    @Override
    public String toSqlQuery() {
        return String.format("SELECT al.id, a.name as author, g.name AS genre, al.year, al.status FROM authors a, genres g, albums al WHERE al.genre=g.id and al.author=a.id");
    }
}

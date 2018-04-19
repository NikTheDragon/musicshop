package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllAuthorsSpecification implements SqlSpecification {

    @Override
    public String toSqlQuery() {
        return String.format("SELECT a.id, a.name, g.name AS genre, a.type, a.status FROM authors a, genres g WHERE a.genre=g.id");
    }
}

package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetDeletedGenreByNameSpecification implements SqlSpecification {
    private final String name;

    public GetDeletedGenreByNameSpecification(String name) {

        this.name = name;
    }

    @Override
    public String toSqlQuery() {

        return String.format("SELECT * FROM genres WHERE name='%1$s' AND status='deleted'", name);
    }
}

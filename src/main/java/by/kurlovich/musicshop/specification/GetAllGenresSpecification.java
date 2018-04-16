package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllGenresSpecification implements SqlSpecification {

    public GetAllGenresSpecification() {
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM genres");
    }
}

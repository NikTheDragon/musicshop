package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllTracksSpecification implements SqlSpecification {

    @Override
    public String toSqlQuery() {

        return String.format("SELECT * FROM tracks");
    }
}

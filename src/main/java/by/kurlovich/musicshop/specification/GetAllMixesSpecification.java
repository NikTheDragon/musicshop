package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllMixesSpecification implements SqlSpecification {

    @Override
    public String toSqlQuery() {

        return String.format("SELECT m.id, m.name, g.name AS genre, m.year, m.status FROM mixes m, genres g WHERE m.genre=g.id");
    }
}

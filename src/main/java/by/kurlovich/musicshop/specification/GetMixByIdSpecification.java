package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetMixByIdSpecification implements SqlSpecification {
    private String mixId;

    public GetMixByIdSpecification(String mixId) {
        this.mixId = mixId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT m.id, m.name, g.name AS genre, m.year, m.status FROM genres g, mixes m WHERE m.genre=g.id AND m.id='%1$s'", mixId);
    }
}

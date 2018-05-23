package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetUserByIdSpecification implements SqlSpecification {
    private String id;

    public GetUserByIdSpecification(String id) {
        this.id = id;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM users WHERE id='%1$s'", id);
    }
}

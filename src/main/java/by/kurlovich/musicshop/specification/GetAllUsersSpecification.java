package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllUsersSpecification implements SqlSpecification {

    @Override
    public String toSqlQuery() {

        return String.format("SELECT * FROM users");
    }
}

package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetUserByLoginSpecification implements SqlSpecification {
    private final String login;

    public GetUserByLoginSpecification (String login) {
        this.login = login;
    }

    @Override
    public String toSqlQuery() {
        return "SELECT * FROM users WHERE login='"+login+"'";
    }
}

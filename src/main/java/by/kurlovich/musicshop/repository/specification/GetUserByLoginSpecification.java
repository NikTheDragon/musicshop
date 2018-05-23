package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetUserByLoginSpecification implements SqlSpecification {
    private final String login;

    public GetUserByLoginSpecification (String login) {
        this.login = login;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM users WHERE login='%1$s'", login);
    }
}

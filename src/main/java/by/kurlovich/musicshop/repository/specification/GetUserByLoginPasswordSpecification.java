package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetUserByLoginPasswordSpecification implements SqlSpecification {
    private final String login;
    private final String password;

    public GetUserByLoginPasswordSpecification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM users WHERE login='%1$s' AND password='%2$s'", login, password);
    }
}

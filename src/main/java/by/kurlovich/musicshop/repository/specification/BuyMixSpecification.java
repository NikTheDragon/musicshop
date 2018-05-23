package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class BuyMixSpecification implements SqlSpecification {
    private String userId;
    private String mixId;

    public BuyMixSpecification (String userId, String mixId) {
        this.userId = userId;
        this.mixId = mixId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("INSERT INTO purchased_mixes (user_id, mix_id) VALUES ('%1$s','%2$s')", userId, mixId);
    }
}

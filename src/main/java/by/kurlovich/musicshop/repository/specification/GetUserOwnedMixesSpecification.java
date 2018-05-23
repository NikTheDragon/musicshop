package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetUserOwnedMixesSpecification implements SqlSpecification {
    private String userId;
    private String sql = "SELECT m.id, m.name, g.name AS genre, m.year, m.status FROM mixes m, genres g, purchased_mixes pm WHERE m.genre=g.id AND pm.mix_id=m.id AND pm.user_id='%1$s'";

    public GetUserOwnedMixesSpecification(String userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return String.format(sql, userId);
    }
}

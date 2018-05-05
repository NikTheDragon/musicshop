package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllMixesWithOwnerIdSpecification implements SqlSpecification {
    private String userId;

    public GetAllMixesWithOwnerIdSpecification (String userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT m.id, m.name, g.name AS genre, m.year, (SELECT COUNT(track_id) FROM mixes_content mc WHERE m.id = mc.mix_id AND mc.status='active' AND m.status='active') AS tracks, m.status, pm.user_id AS owner FROM mixes m LEFT OUTER JOIN purchased_mixes pm ON pm.mix_id = m.id AND pm.user_id='%1$s', genres g WHERE m.genre=g.id", userId);
    }
}

package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetMixContentWithOwnersIdByMixIdSpecification implements SqlSpecification {
    private String userId;
    private String mixId;

    public GetMixContentWithOwnersIdByMixIdSpecification (String userId, String mixId) {
        this.userId = userId;
        this.mixId = mixId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT t.id, t.name, a.name AS author, g.name AS genre, t.year, t.length, t.status, pt.user_id AS owner FROM tracks t LEFT OUTER JOIN purchased_tracks pt ON pt.track_id = t.id AND pt.user_id='%1$s', genres g, authors a, mixes_content mc WHERE t.genre=g.id AND t.author=a.id AND mc.track_id=t.id AND mc.mix_id='%2$s' AND mc.status='active' AND t.status='active'", userId, mixId);
    }
}

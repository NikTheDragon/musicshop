package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetMixContentByMixIdSpecification implements SqlSpecification {
    String mixId;

    public GetMixContentByMixIdSpecification (String mixId) {
        this.mixId = mixId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT m.mix_id, m.track_id, t.name as name, a.name AS author, m.status FROM mixes_content m, tracks t, authors a WHERE m.mix_id='%1$s' AND t.id=m.track_id AND a.id=t.author", mixId);
    }
}

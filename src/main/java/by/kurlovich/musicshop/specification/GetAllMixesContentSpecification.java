package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllMixesContentSpecification implements SqlSpecification {

    @Override
    public String toSqlQuery() {
        return String.format("SELECT m.mix_id, m.track_id, t.name AS name, a.name AS author, m.status FROM mixes_content m, tracks t , authors a WHERE t.id=m.track_id AND a.id=t.author");
    }
}

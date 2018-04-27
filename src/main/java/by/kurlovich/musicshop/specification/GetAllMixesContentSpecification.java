package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAllMixesContentSpecification implements SqlSpecification {

    @Override
    public String toSqlQuery() {
        return String.format("SELECT mixes_content.mix_id, mixes_content.track_id, tracks.name AS name, authors.name AS author, mixes_content.status FROM mixes_content, tracks, authors WHERE tracks.id=mixes_content.track_id AND authors.id=tracks.author");
    }
}

package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAlbumContentByParamSpecification implements SqlSpecification {
    String param;

    public GetAlbumContentByParamSpecification(String param) {
        this.param = param;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT a.album_id, a.track_id, t.name as name, t.length, a.status FROM albums_content a, tracks t WHERE a.album_id='%1$s' AND t.id=a.track_id", param);
    }
}

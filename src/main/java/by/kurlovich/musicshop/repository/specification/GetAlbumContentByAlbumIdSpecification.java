package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAlbumContentByAlbumIdSpecification implements SqlSpecification {
    private String albumId;

    public GetAlbumContentByAlbumIdSpecification(String albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT a.album_id, a.track_id, t.name as name, t.length, a.status FROM albums_content a, tracks t WHERE a.album_id='%1$s' AND t.id=a.track_id", albumId);
    }
}

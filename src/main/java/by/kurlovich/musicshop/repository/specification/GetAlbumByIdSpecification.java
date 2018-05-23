package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetAlbumByIdSpecification implements SqlSpecification {
    String albumId;

    public GetAlbumByIdSpecification(String albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT al.id, al.name, a.name AS author, g.name AS genre, al.year, al.status FROM authors a, genres g, albums al WHERE al.genre=g.id AND al.author=a.id AND al.id='%1$s'", albumId);
    }
}

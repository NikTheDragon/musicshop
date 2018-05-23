package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetUserOwnedAlbumsSpecification implements SqlSpecification {
    private String userId;
    private String sql = "SELECT al.id, al.name, a.name AS author, g.name AS genre, al.year, al.status FROM authors a, genres g, albums al, purchased_albums pa WHERE al.genre=g.id AND al.author=a.id AND pa.album_id=al.id AND pa.user_id='%1$s'";

    public GetUserOwnedAlbumsSpecification(String userId) {
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return String.format(sql, userId);
    }
}

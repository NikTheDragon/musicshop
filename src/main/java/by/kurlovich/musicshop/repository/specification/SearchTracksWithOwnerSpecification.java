package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.repository.SqlSpecification;

public class SearchTracksWithOwnerSpecification implements SqlSpecification {
    private static String sql = "SELECT t.id, t.name, a.name AS author, g.name AS genre, t.year, t.length, t.status, pt.user_id AS owner FROM tracks t LEFT OUTER JOIN purchased_tracks pt ON pt.track_id = t.id AND pt.user_id='%1$s', genres g, authors a WHERE t.genre=g.id AND t.author=a.id AND t.name LIKE '%%%2$s%%' AND a.name LIKE '%%%3$s%%' AND g.name LIKE '%%%4$s%%' AND t.year LIKE '%%%5$s%%'";
    private String userId;
    private SearchData searchData;

    public SearchTracksWithOwnerSpecification(SearchData searchData, String userId) {
        this.searchData = searchData;
        this.userId = userId;
    }

    @Override
    public String toSqlQuery() {
        return String.format(sql, userId, searchData.getName(), searchData.getAuthor(), searchData.getGenre(), searchData.getYear());
    }
}

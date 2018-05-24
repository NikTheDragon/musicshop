package by.kurlovich.musicshop.repository.specification;

import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.repository.SqlSpecification;

public class SearchAuthorsSpecification implements SqlSpecification {
    private String sql = "SELECT a.id, a.name, g.name AS genre, a.type, a.status FROM authors a, genres g WHERE a.genre=g.id AND a.name LIKE '%%%1$s%%' AND g.name LIKE '%%%2$s%%' AND a.type LIKE '%%%3$s%%'";
    private SearchData searchData;

    public SearchAuthorsSpecification(SearchData searchData) {
        this.searchData = searchData;
    }

    @Override
    public String toSqlQuery() {
        return String.format(sql, searchData.getName(), searchData.getGenre(), searchData.getType());
    }
}

package by.kurlovich.musicshop.specification;

import by.kurlovich.musicshop.repository.SqlSpecification;

public class GetTracksByFieldsSpecification implements SqlSpecification {
    private String name;
    private String author;
    private String genre;
    private String year;
    private String length;

    public GetTracksByFieldsSpecification(String name, String author, String genre, String year, String length) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.length = length;
    }

    @Override
    public String toSqlQuery() {
        return String.format("SELECT * FROM tracks WHERE name='%1$s', author='%2$s', genre='%3$s', year='%4$s', length='%5$s'", name, author, genre, year, length);
    }
}

package by.kurlovich.musicshop.entity;

import java.io.Serializable;
import java.util.Objects;

public class SearchData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String author;
    private String genre;
    private String year;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchData that = (SearchData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(author, that.author) &&
                Objects.equals(genre, that.genre) &&
                Objects.equals(year, that.year) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, author, genre, year, type);
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", year='" + year + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

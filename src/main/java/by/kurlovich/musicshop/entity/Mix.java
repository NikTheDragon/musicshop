package by.kurlovich.musicshop.entity;

import java.util.Objects;

public class Mix {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String genre;
    private String year;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mix mix = (Mix) o;
        return Objects.equals(id, mix.id) &&
                Objects.equals(name, mix.name) &&
                Objects.equals(genre, mix.genre) &&
                Objects.equals(year, mix.year) &&
                Objects.equals(status, mix.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, genre, year, status);
    }
}

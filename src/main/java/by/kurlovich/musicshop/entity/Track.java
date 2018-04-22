package by.kurlovich.musicshop.entity;

import java.util.Objects;

public class Track {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String author;
    private String genre;
    private String year;
    private String length;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
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
        Track track = (Track) o;
        return Objects.equals(id, track.id) &&
                Objects.equals(name, track.name) &&
                Objects.equals(author, track.author) &&
                Objects.equals(genre, track.genre) &&
                Objects.equals(year, track.year) &&
                Objects.equals(length, track.length) &&
                Objects.equals(status, track.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, author, genre, year, length, status);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", year='" + year + '\'' +
                ", length='" + length + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

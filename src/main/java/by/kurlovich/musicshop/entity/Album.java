package by.kurlovich.musicshop.entity;

import java.util.Objects;

public class Album {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String author;
    private String genre;
    private int year;
    private int tracksCount;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTracksCount() {
        return tracksCount;
    }

    public void setTracksCount(int tracksCount) {
        this.tracksCount = tracksCount;
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
        Album album = (Album) o;
        return year == album.year &&
                tracksCount == album.tracksCount &&
                Objects.equals(id, album.id) &&
                Objects.equals(name, album.name) &&
                Objects.equals(author, album.author) &&
                Objects.equals(genre, album.genre) &&
                Objects.equals(status, album.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, author, genre, year, tracksCount, status);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", tracksCount=" + tracksCount +
                ", status='" + status + '\'' +
                '}';
    }
}

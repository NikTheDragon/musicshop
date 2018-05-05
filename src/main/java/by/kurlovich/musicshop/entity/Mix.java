package by.kurlovich.musicshop.entity;

import java.util.Objects;

public class Mix {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String genre;
    private String year;
    private int tracksCount;
    private String status;
    private String ownerId;

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

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mix mix = (Mix) o;
        return tracksCount == mix.tracksCount &&
                Objects.equals(id, mix.id) &&
                Objects.equals(name, mix.name) &&
                Objects.equals(genre, mix.genre) &&
                Objects.equals(year, mix.year) &&
                Objects.equals(status, mix.status) &&
                Objects.equals(ownerId, mix.ownerId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, genre, year, tracksCount, status, ownerId);
    }

    @Override
    public String toString() {
        return "Mix{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", year='" + year + '\'' +
                ", tracksCount=" + tracksCount +
                ", status='" + status + '\'' +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }
}

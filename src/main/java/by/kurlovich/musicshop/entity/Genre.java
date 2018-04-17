package by.kurlovich.musicshop.entity;

import java.util.Objects;

public class Genre {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) &&
                Objects.equals(name, genre.name) &&
                Objects.equals(status, genre.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, status);
    }
}

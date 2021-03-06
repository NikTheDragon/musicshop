package by.kurlovich.musicshop.entity;

import java.util.Objects;

public class Content {
    private static final long serialVersionUID = 1L;
    private String entityId;
    private String trackId;
    private String trackName;
    private String authorName;
    private String length;
    private String status;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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
        Content content = (Content) o;
        return Objects.equals(entityId, content.entityId) &&
                Objects.equals(trackId, content.trackId) &&
                Objects.equals(trackName, content.trackName) &&
                Objects.equals(authorName, content.authorName) &&
                Objects.equals(length, content.length) &&
                Objects.equals(status, content.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(entityId, trackId, trackName, authorName, length, status);
    }
}

package by.kurlovich.musicshop.repository;

public interface SqlSpecification extends Specification {
    String toSqlQuery();
}

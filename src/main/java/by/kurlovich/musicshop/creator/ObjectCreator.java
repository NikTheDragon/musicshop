package by.kurlovich.musicshop.creator;

import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.User;
import com.sun.istack.internal.NotNull;

import java.util.Map;

public class ObjectCreator {
    public static User createUser(Map<String, String[]> requestMap) {
        User user = new User();

        user.setId(requestMap.get("submit_id")[0]);
        user.setName(requestMap.get("submit_name")[0]);
        user.setSurname(requestMap.get("submit_surname")[0]);
        user.setLogin(requestMap.get("submit_login")[0]);
        user.setPassword(requestMap.get("submit_password")[0]);
        user.setEmail(requestMap.get("submit_email")[0]);
        user.setRole(requestMap.get("submit_role")[0]);
        user.setStatus(requestMap.get("submit_status")[0]);
        user.setPoints(Integer.parseInt(requestMap.get("submit_points")[0]));

        return user;
    }

    public static Album createAlbum(Map<String, String[]> requestMap) {
        Album album = new Album();

        album.setId(requestMap.get("submit_id")[0]);
        album.setName(requestMap.get("submit_name")[0]);
        album.setGenre(requestMap.get("submit_genre")[0]);
        album.setAuthor(requestMap.get("submit_author")[0]);
        album.setYear(Integer.parseInt(requestMap.get("submit_year")[0]));
        album.setStatus(requestMap.get("submit_status")[0]);

        return album;
    }

    public static Author createAuthor (Map<String, String[]> requestMap) {
        Author author = new Author();

        author.setId(requestMap.get("submit_id")[0]);
        author.setName(requestMap.get("submit_name")[0]);
        author.setGenre(requestMap.get("submit_genre")[0]);
        author.setType(requestMap.get("submit_type")[0]);
        author.setStatus(requestMap.get("submit_status")[0]);

        return author;
    }
}

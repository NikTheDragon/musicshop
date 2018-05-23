package by.kurlovich.musicshop.util.creator;

import by.kurlovich.musicshop.entity.*;

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

    public static Author createAuthor(Map<String, String[]> requestMap) {
        Author author = new Author();

        author.setId(requestMap.get("submit_id")[0]);
        author.setName(requestMap.get("submit_name")[0]);
        author.setGenre(requestMap.get("submit_genre")[0]);
        author.setType(requestMap.get("submit_type")[0]);
        author.setStatus(requestMap.get("submit_status")[0]);

        return author;
    }

    public static Genre createGenre(Map<String, String[]> requestMap) {
        Genre genre = new Genre();

        genre.setId(requestMap.get("submit_id")[0]);
        genre.setName(requestMap.get("submit_name")[0]);
        genre.setStatus(requestMap.get("submit_status")[0]);

        return genre;
    }

    public static Mix createMix(Map<String, String[]> requestMap) {
        Mix mix = new Mix();

        mix.setId(requestMap.get("submit_id")[0]);
        mix.setName(requestMap.get("submit_name")[0]);
        mix.setGenre(requestMap.get("submit_genre")[0]);
        mix.setYear(requestMap.get("submit_year")[0]);
        mix.setStatus(requestMap.get("submit_status")[0]);

        return mix;
    }

    public static Track createTrack(Map<String, String[]> requestMap) {
        Track track = new Track();

        track.setId(requestMap.get("submit_id")[0]);
        track.setName(requestMap.get("submit_name")[0]);
        track.setAuthor(requestMap.get("submit_author")[0]);
        track.setGenre(requestMap.get("submit_genre")[0]);
        track.setYear(requestMap.get("submit_year")[0]);
        track.setLength(requestMap.get("submit_length")[0]);
        track.setStatus(requestMap.get("submit_status")[0]);

        return track;
    }

    public static SearchData createSearchData(Map<String, String[]> requestMap) {
        SearchData searchData = new SearchData();

        if (requestMap.get("search_name") == null) {
            searchData.setName("");
        } else {
            searchData.setName(requestMap.get("search_name")[0]);
        }

        if (requestMap.get("search_author") == null) {
            searchData.setAuthor("");
        } else {
            searchData.setAuthor(requestMap.get("search_author")[0]);
        }

        if (requestMap.get("search_genre") == null) {
            searchData.setGenre("");
        } else {
            searchData.setGenre(requestMap.get("search_genre")[0]);
        }

        if (requestMap.get("search_year") == null) {
            searchData.setYear("");
        } else {
            searchData.setYear(requestMap.get("search_year")[0]);
        }

        if (requestMap.get("search_type") == null) {
            searchData.setType("");
        } else {
            searchData.setType(requestMap.get("search_type")[0]);
        }

        return searchData;
    }
}

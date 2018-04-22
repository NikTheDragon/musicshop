package by.kurlovich.musicshop.pages;

public enum PageStore {
    MAIN_PAGE("/jsp/common/main.jsp"),
    ERROR_PAGE("/jsp/error/error.jsp"),
    REG_PAGE("/jsp/common/registration.jsp"),
    REGISTRATION_COMPLETE("/jsp/common/reg_complete.jsp"),
    USER_PAGE("/jsp/user/user_main.jsp"),
    ADMIN_PAGE("/jsp/admin/admin_main.jsp"),
    EDIT_TRACKS_PAGE("/jsp/admin/edit_tracks.jsp"),
    EDIT_GENRES_PAGE("/jsp/admin/edit_genres.jsp"),
    EDIT_AUTHORS_PAGE("/jsp/admin/edit_authors.jsp"),
    EDIT_ALBUMS_PAGE("/jsp/admin/edit_albums.jsp");

    private String pageName;

    PageStore(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}

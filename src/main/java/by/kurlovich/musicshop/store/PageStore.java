package by.kurlovich.musicshop.store;

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
    EDIT_MIXES_PAGE("/jsp/admin/edit_mixes.jsp"),
    EDIT_ALBUMS_PAGE("/jsp/admin/edit_albums.jsp"),
    EDIT_MIXES_CONTENT_PAGE("/jsp/admin/edit_mixes_content.jsp"),
    EDIT_ALBUMS_CONTENT_PAGE("/jsp/admin/edit_albums_content.jsp");

    private String pageName;

    PageStore(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}

package by.kurlovich.musicshop.web.pages;

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
    EDIT_ALBUMS_CONTENT_PAGE("/jsp/admin/edit_albums_content.jsp"),
    SHOW_EDIT_USER_PAGE("/jsp/admin/edit_user.jsp"),
    SHOW_USERS_PAGE("/jsp/admin/show_users.jsp"),
    PERSONAL_PAGE("/jsp/user/personal_page.jsp"),
    POINTS_PAGE("/jsp/user/point_management.jsp"),
    SHOW_MY_TRACKS("/jsp/user/my_tracks.jsp"),
    SHOW_MY_ALBUMS("/jsp/user/my_albums.jsp"),
    SHOW_MY_MIXES("/jsp/user/my_mixes.jsp"),
    SHOW_TRACKS_PAGE("/jsp/common/show_tracks.jsp"),
    SHOW_ALBUMS_PAGE("/jsp/common/show_albums.jsp"),
    SHOW_MIXES_PAGE("/jsp/common/show_mixes.jsp"),
    SHOW_MIX_CONTENT_PAGE("/jsp/common/show_mix_content.jsp"),
    SHOW_ALBUM_CONTENT_PAGE("/jsp/common/show_album_content.jsp");

    private String pageName;

    PageStore(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}

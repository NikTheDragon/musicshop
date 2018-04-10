package by.kurlovich.musicshop.pagefactory;

public enum PageStore {
    MAIN_PAGE("/jsp/common/main.jsp"),
    ERROR_PAGE("/jsp/error/error.jsp"),
    REG_PAGE("/jsp/common/registration.jsp"),
    MESSAGE_PAGE("/jsp/common/message.jsp"),
    REGISTRATION_COMPLETE("/jsp/common/reg_complete.jsp"),
    USER_PAGE("/jsp/user/user_main.jsp"),
    ADMIN_PAGE("/jsp/admin/admin_main.jsp"),
    EDIT_TRACKS_PAGE("/jsp/admin/edit_tracks.jsp"),
    EDIT_GENRES_PAGE("/jsp/admin/edit_genres.jsp");

    private String pageName;

    PageStore(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}

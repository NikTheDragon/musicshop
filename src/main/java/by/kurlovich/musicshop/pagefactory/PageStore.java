package by.kurlovich.musicshop.pagefactory;

public enum PageStore {
    MAIN_PAGE("/jsp/common/main.jsp"),
    ERROR_PAGE("/jsp/error/error.jsp"),
    REG_PAGE("/jsp/common/registration.jsp"),
    MESSAGE_PAGE("/jsp/common/message.jsp"),
    REGISTRATION_COMPLETE("/jsp/common/reg_complete.jsp");

    private String pageName;

    PageStore(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}

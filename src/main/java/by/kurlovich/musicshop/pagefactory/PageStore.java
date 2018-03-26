package by.kurlovich.musicshop.pagefactory;

public enum PageStore {
    MAIN_PAGE("/jsp/main.jsp"),
    ERROR_PAGE("/jsp/error/error.jsp"),
    REG_PAGE("/jsp/registration.jsp"),
    MESSAGE_PAGE("/jsp/message.jsp");

    private String pageName;

    PageStore(String pageName) {
        this.pageName = pageName;
    }

    public String getPageName() {
        return pageName;
    }
}

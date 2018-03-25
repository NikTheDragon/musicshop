package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;

public class CommonReceiver {
    private static final String MAIN_PAGE = "/jsp/main.jsp";
    private static final CommonReceiver instance = new CommonReceiver();

    public static CommonReceiver getInstance() {
        return instance;
    }

    public CommandResult showMainPage(RequestContent requestContent) {
        requestContent.getRequest().getSession(true).setAttribute("url",MAIN_PAGE);
        return new CommandResult(CommandResult.ResponseType.FORWARD, MAIN_PAGE);
    }

    public CommandResult changeLanguage(RequestContent requestContent) {
        String locale = requestContent.getRequest().getParameter("locale");

        String url = (String) requestContent.getRequest().getSession(true).getAttribute("url");
        requestContent.getRequest().getSession(true).setAttribute("locale", locale);

        return new CommandResult(CommandResult.ResponseType.FORWARD, url);
    }
}

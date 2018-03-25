package by.kurlovich.musicshop.receiver;

import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;

public class ErrorReceiver {
    private static final String ERROR_PAGE = "/jsp/error/error.jsp";
    private static final ErrorReceiver instance = new ErrorReceiver();

    public static ErrorReceiver getInstance() {

        return instance;
    }

    public CommandResult showErrorPage(RequestContent requestContent) {
        requestContent.getRequest().getSession(true).setAttribute("url",ERROR_PAGE);
        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

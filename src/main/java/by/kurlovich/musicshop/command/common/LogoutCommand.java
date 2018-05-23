package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    private final static String MAIN_PAGE = PageStore.MAIN_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession().invalidate();
        request.getSession(true).setAttribute("url", MAIN_PAGE);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, MAIN_PAGE);
    }
}

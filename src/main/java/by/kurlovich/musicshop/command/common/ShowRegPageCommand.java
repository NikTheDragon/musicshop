package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowRegPageCommand implements Command {
    private static final String REG_PAGE = PageStore.REG_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession(true).setAttribute("url", REG_PAGE);
        return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
    }
}

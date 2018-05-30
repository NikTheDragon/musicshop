package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowErrorPageCommand implements Command {
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) {

        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

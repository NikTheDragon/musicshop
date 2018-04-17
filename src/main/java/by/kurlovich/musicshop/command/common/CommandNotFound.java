package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.pagefactory.PageStore;

import javax.servlet.http.HttpServletRequest;

public class CommandNotFound implements Command {
    private String errorPage = PageStore.ERROR_PAGE.getPageName();

    public CommandNotFound() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {

        request.getSession(true).setAttribute("url", errorPage);
        return new CommandResult(CommandResult.ResponseType.FORWARD, errorPage);
    }
}

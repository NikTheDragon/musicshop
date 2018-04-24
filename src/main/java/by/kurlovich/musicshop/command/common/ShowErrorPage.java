package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowErrorPage implements Command {
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();

    public ShowErrorPage() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {

        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowMainPageCommand implements Command {
    private String mainPage = PageStore.MAIN_PAGE.getPageName();

    public ShowMainPageCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession(true).setAttribute("url", mainPage);
        return new CommandResult(CommandResult.ResponseType.FORWARD, mainPage);
    }
}

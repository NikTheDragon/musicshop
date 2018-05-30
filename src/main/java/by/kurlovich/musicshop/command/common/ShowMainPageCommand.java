package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowMainPageCommand implements Command {
    private String mainPage = PageStore.MAIN_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession(true).setAttribute("url", mainPage);
        return new CommandResult(CommandResult.ResponseType.FORWARD, mainPage);
    }
}

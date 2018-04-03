package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.pagefactory.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowMainPage implements Command {
    private String page = PageStore.MAIN_PAGE.getPageName();

    public ShowMainPage() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession(true).setAttribute("url", page);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }
}

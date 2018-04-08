package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.pagefactory.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowUserPage implements Command {
    private String page = PageStore.USER_PAGE.getPageName();

    public ShowUserPage() {

    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession(true).setAttribute("url", page);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }
}

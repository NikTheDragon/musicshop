package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;
import by.kurlovich.musicshop.pagefactory.PageStore;

public class CommandNotFound implements Command{
    private String page = PageStore.ERROR_PAGE.getPageName();
    private static final CommandNotFound instance = new CommandNotFound();

    public static CommandNotFound getInstance() {
        return instance;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {

        requestContent.getRequest().getSession(true).setAttribute("url", page);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }
}

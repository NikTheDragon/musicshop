package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;
import by.kurlovich.musicshop.pagefactory.PageStore;

public class ShowRegPage implements Command {
    private String page = PageStore.REG_PAGE.getPageName();
    public static final ShowRegPage instance = new ShowRegPage();

    public static ShowRegPage getInstance() {
        return instance;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        requestContent.getRequest().getSession(true).setAttribute("url", page);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }
}

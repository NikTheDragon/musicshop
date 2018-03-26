package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;

public class ChangeLanguage implements Command{
    private final static ChangeLanguage instance = new ChangeLanguage();

    public static ChangeLanguage getInstance() {
        return instance;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        String locale = requestContent.getRequest().getParameter("locale");
        String url = (String) requestContent.getRequest().getSession(true).getAttribute("url");

        requestContent.getRequest().getSession(true).setAttribute("locale", locale);

        return new CommandResult(CommandResult.ResponseType.FORWARD, url);
    }
}

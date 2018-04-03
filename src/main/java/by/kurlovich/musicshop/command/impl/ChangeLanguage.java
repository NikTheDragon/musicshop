package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class ChangeLanguage implements Command {
    public ChangeLanguage() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String locale = request.getParameter("locale");
        String url = (String) request.getSession(true).getAttribute("url");

        request.getSession(true).setAttribute("locale", locale);

        return new CommandResult(CommandResult.ResponseType.FORWARD, url);
    }
}

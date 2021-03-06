package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class BackToPageCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {

        String url = (String) request.getSession(true).getAttribute("url");
        return new CommandResult(CommandResult.ResponseType.FORWARD, url);
    }
}

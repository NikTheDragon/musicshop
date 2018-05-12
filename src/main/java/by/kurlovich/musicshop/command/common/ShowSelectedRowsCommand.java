package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowSelectedRowsCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String currentPage = request.getParameter("currentPage");
        String currentURI = request.getParameter("currentURI");

        request.setAttribute("currentPage", currentPage);
        return new CommandResult(CommandResult.ResponseType.FORWARD, currentURI);
    }
}

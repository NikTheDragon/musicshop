package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;

import javax.servlet.http.HttpServletRequest;

public class ShowSelectedRowsCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String currentPageNumber = request.getParameter("currentPageNumber");
        String currentURI = request.getParameter("currentURI");

        request.setAttribute("currentPageNumber", currentPageNumber);
        return new CommandResult(CommandResult.ResponseType.FORWARD, currentURI);
    }
}

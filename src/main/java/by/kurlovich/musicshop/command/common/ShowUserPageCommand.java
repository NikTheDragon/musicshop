package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import by.kurlovich.musicshop.validator.AccessValidator;

public class ShowUserPageCommand implements Command {
    private final static String USER_PAGE = PageStore.USER_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();

    public ShowUserPageCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        List<String> accessRoles = Arrays.asList("admin", "user");
        String userRole = (String) request.getSession(true).getAttribute("role");

        if (AccessValidator.validate(accessRoles, userRole)) {
            request.getSession(true).setAttribute("url", USER_PAGE);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, USER_PAGE);
        }

        request.getSession(true).setAttribute("url", ERROR_PAGE);
        request.setAttribute("nocommand", "Access denied!");
        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

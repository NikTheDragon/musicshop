package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ShowUserPageCommand implements Command {
    private static final String USER_PAGE = PageStore.USER_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) {
        List<String> accessRoles = Arrays.asList("admin", "user");
        String userRole = (String) request.getSession(true).getAttribute("role");

        if (AccessValidator.validate(accessRoles, userRole)) {
            request.getSession(true).setAttribute("url", USER_PAGE);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, USER_PAGE);
        }

        request.getSession(true).setAttribute("url", ERROR_PAGE);
        request.setAttribute("message", "Access denied!");
        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

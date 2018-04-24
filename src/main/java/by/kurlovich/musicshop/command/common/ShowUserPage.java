package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import by.kurlovich.musicshop.validator.AccessValidator;

public class ShowUserPage implements Command {
    private String page = PageStore.USER_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private List<String> accessRoles = Arrays.asList("admin", "user");
    private AccessValidator accessValidator = new AccessValidator();

    public ShowUserPage() {

    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String userRole = (String) request.getSession(true).getAttribute("role");

        if (accessValidator.validate(accessRoles, userRole)) {
            request.getSession(true).setAttribute("url", page);
            return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
        }

        request.getSession(true).setAttribute("url", ERROR_PAGE);
        request.setAttribute("nocommand", "Access denied!");
        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

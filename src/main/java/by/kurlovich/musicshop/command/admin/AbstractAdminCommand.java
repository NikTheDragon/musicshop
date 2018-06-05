package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class AbstractAdminCommand implements Command {

    boolean isAuthorised(HttpServletRequest request) {
        List<String> accessRoles = Arrays.asList("admin");
        return AccessValidator.validate(accessRoles, getUserRole(request));
    }

    String getUserRole(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute("role");
    }

    CommandResult createAccessDeniedResult(HttpServletRequest request) {
        return createErrorResult(request, "denied");
    }

    CommandResult createFailedResult(HttpServletRequest request, String message) {
        return createErrorResult(request, message);
    }

    CommandResult createUnvalidatedResult(HttpServletRequest request, Map<String, String> validationMessages, String page) {
        request.setAttribute("messages", validationMessages);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }

    CommandResult createOKResult(HttpServletRequest request, String page) {
        request.getSession(true).setAttribute("url", page);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }

    private CommandResult createErrorResult(HttpServletRequest request, String message) {
        request.setAttribute("message", message);
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageStore.ERROR_PAGE.getPageName());
    }

}

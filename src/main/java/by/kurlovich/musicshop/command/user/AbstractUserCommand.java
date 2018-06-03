package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

abstract class AbstractUserCommand implements Command {

    boolean isAuthorised(HttpServletRequest request) {
        List<String> accessRoles = Arrays.asList("admin", "user");
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

    CommandResult createOKResult(HttpServletRequest request, String page) {
        request.getSession(true).setAttribute("url", page);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
    }

    CommandResult createUnvalidatedLogPassResult(HttpServletRequest request, Map<String, String> validationMessages, String page) {
        request.setAttribute("oldPasswordResult", validationMessages.get("oldPasswordResult"));
        request.setAttribute("newPasswordResult", validationMessages.get("newPasswordResult"));
        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }

    CommandResult createUnvalidatedResult(HttpServletRequest request, Map<String, String> validationMessages, String page) {
        request.setAttribute("messages", validationMessages);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }

    boolean isLogPassValidated (Map<String, String> validationMessages) {
        return (Boolean.parseBoolean(validationMessages.get("oldPasswordResult")) &&
                Boolean.parseBoolean(validationMessages.get("newPasswordResult")));
    }

    User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession(true).getAttribute("user");
    }

    private CommandResult createErrorResult(HttpServletRequest request, String message) {
        request.getSession(true).setAttribute("url", PageStore.ERROR_PAGE.getPageName());
        request.setAttribute("message", message);
        return new CommandResult(CommandResult.ResponseType.FORWARD, PageStore.ERROR_PAGE.getPageName());
    }
}

package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.validator.AccessValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ShowPointManagementPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPointManagementPageCommand.class);
    private final static String POINTS_PAGE = PageStore.POINTS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        List<String> accessRoles = Arrays.asList("admin", "user");
        String userRole = (String) request.getSession(true).getAttribute("role");

        if (AccessValidator.validate(accessRoles, userRole)) {
            request.getSession(true).setAttribute("url", POINTS_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, POINTS_PAGE);
        }

        request.getSession(true).setAttribute("url", ERROR_PAGE);
        request.setAttribute("message", "denied");
        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

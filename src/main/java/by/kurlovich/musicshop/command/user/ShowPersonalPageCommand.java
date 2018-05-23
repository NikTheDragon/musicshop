package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;

import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ShowPersonalPageCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPersonalPageCommand.class);
    private final static String PERSONAL_PAGE = PageStore.PERSONAL_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {

        List<String> accessRoles = Arrays.asList("admin", "user");
        String userRole = (String) request.getSession(true).getAttribute("role");

        if (AccessValidator.validate(accessRoles, userRole)) {

            request.getSession(true).setAttribute("url", PERSONAL_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PERSONAL_PAGE);
        }

        request.getSession(true).setAttribute("url", ERROR_PAGE);
        request.setAttribute("message", "denied");
        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class AddPointsCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPointManagementPageCommand.class);
    private final static String POINTS_PAGE = PageStore.POINTS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private UserReceiver receiver;

    public AddPointsCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin", "user");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                User currentUser = (User) request.getSession(true).getAttribute("user");
                String tariff = (String) request.getParameter("submit_tariff");

                if (receiver.updateUser(addPoints(tariff, currentUser))) {

                    request.getSession(true).setAttribute("url", POINTS_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, POINTS_PAGE);
                }
                request.setAttribute("message", "unupdate");
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in AddPointsCommand.\n" + e, e);
        }
    }

    private User addPoints(String tariff, User user) {
        int actualPoints = user.getPoints();

        switch (tariff) {
            case "min":
                user.setPoints(actualPoints + 1);
                break;
            case "opt":
                user.setPoints(actualPoints + 10);
                break;
            case "max":
                user.setPoints(actualPoints + 50);
                break;
            default:
        }

        return user;
    }
}

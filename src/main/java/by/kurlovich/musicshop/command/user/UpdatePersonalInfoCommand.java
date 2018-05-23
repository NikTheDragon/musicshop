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

public class UpdatePersonalInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePersonalInfoCommand.class);
    private final static String PERSONAL_PAGE = PageStore.PERSONAL_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final UserReceiver receiver;

    public UpdatePersonalInfoCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin", "user");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                User currentUser = createUser(request);

                if (receiver.updateUser(currentUser)) {
                    request.getSession(true).setAttribute("user", currentUser);
                    request.getSession(true).setAttribute("url", PERSONAL_PAGE);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, PERSONAL_PAGE);
                }
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdatePersonalInfoCommand.\n" + e, e);
        }
    }

    private User createUser(HttpServletRequest request) {
        User user = new User();
        User sessionUser = (User) request.getSession(true).getAttribute("user");

        user.setId(request.getParameter("submit_id"));
        user.setName(request.getParameter("submit_name"));
        user.setSurname(request.getParameter("submit_surname"));
        user.setLogin(request.getParameter("submit_login"));
        user.setPassword(request.getParameter("submit_password"));
        user.setEmail(request.getParameter("submit_email"));
        user.setRole(sessionUser.getRole());
        user.setStatus(sessionUser.getStatus());
        user.setPoints(sessionUser.getPoints());

        return user;
    }
}

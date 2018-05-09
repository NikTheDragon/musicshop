package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.validator.AccessValidator;
import by.kurlovich.musicshop.validator.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class UpdateUserCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateUserCommand.class);
    private final static String SHOW_EDIT_USER_PAGE = PageStore.SHOW_EDIT_USER_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final UserReceiver receiver;

    public UpdateUserCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                if (ObjectValidator.validateUser(request)) {
                    User currentUser = createUser(request);

                    if (receiver.updateUser(currentUser)) {

                        request.getSession(true).setAttribute("userInfo", currentUser);
                        request.getSession(true).setAttribute("url", SHOW_EDIT_USER_PAGE);
                        return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_EDIT_USER_PAGE);
                    }

                } else {
                    return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_EDIT_USER_PAGE);
                }
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditTracksPageCommand.\n" + e, e);
        }
    }

    private User createUser(HttpServletRequest request) {
        User user = new User();

        user.setId(request.getParameter("submit_id"));
        user.setName(request.getParameter("submit_name"));
        user.setSurname(request.getParameter("submit_surname"));
        user.setLogin(request.getParameter("submit_login"));
        user.setPassword(request.getParameter("submit_password"));
        user.setEmail(request.getParameter("submit_email"));
        user.setRole(request.getParameter("submit_role"));
        user.setStatus(request.getParameter("submit_status"));
        user.setPoints(Integer.parseInt(request.getParameter("submit_points")));

        return user;
    }
}

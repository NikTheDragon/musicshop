package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegNewUserComand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegNewUserComand.class);
    private static final String REG_PAGE = PageStore.REG_PAGE.getPageName();
    private static final String REGISTRATION_COMPLETE = PageStore.REGISTRATION_COMPLETE.getPageName();
    private UserReceiver receiver;

    public RegNewUserComand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        Map<String, String> messageMap;
        User user = createUser(request);
        LOGGER.debug("get user: {}", user);

        request.setAttribute("user", user);

        try {
            messageMap = receiver.validateUser(user);

            if (!Boolean.parseBoolean(messageMap.get("validate"))) {
                request.setAttribute("messages", messageMap);
                return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
            }

            if (receiver.addNewUser(user)) {

                User currentUser = receiver.loginUser(user.getLogin(), user.getPassword());

                request.getSession(true).setAttribute("url", REGISTRATION_COMPLETE);
                request.getSession(true).setAttribute("user", currentUser);
                request.getSession(true).setAttribute("role", currentUser.getRole());
                return new CommandResult(CommandResult.ResponseType.REDIRECT, REGISTRATION_COMPLETE);
            } else {
                messageMap.put("loginMessage", "login");
                request.setAttribute("messages", messageMap);
                return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
            }
        } catch (ReceiverException e) {
            throw new CommandException("Exception in RegNewUserCommand.\n" + e, e);
        }
    }

    private User createUser(HttpServletRequest request) {
        User user = new User();

        user.setName(request.getParameter("submit_name"));
        user.setSurname(request.getParameter("submit_surname"));
        user.setLogin(request.getParameter("submit_login"));
        user.setPassword(request.getParameter("submit_password"));
        user.setEmail(request.getParameter("submit_email"));
        user.setRole("user");
        user.setStatus("active");
        user.setPoints(0);

        return user;
    }
}

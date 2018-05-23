package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.util.validator.FieldValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Class for checking user's login and password and if they are ok user will be logged to system.
 */

public class LoginUserCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUserCommand.class);
    private final static String MAIN_PAGE = PageStore.MAIN_PAGE.getPageName();
    private final static String USER_PAGE = PageStore.USER_PAGE.getPageName();
    private final static String ADMIN_PAGE = PageStore.ADMIN_PAGE.getPageName();
    private UserReceiver receiver;

    public LoginUserCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            Map<String, String[]> requestMap = request.getParameterMap();
            String[] login = requestMap.get("login");
            String[] password = requestMap.get("password");

            String loginValidateResult = FieldValidator.validateLogPasField(login);
            String passwordValidateResult = FieldValidator.validateLogPasField(password);

            if (Boolean.parseBoolean(loginValidateResult) && Boolean.parseBoolean(passwordValidateResult)) {
                LOGGER.debug("Attempt to login user with login={}, and password={}", login[0], password[0]);

                User user = receiver.loginUser(login[0], password[0]);

                if (user != null) {
                    String startPage = getStartPage(user);

                    return showStartPage(user, startPage, request);

                } else {
                    request.setAttribute("loginError", "noLP");
                    request.setAttribute("passwordError", "noLP");
                }

            } else {
                request.setAttribute("loginError", loginValidateResult);
                request.setAttribute("passwordError", passwordValidateResult);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, MAIN_PAGE);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in LoginUserCommand.\n" + e, e);
        }
    }

    /**
     * Method to select start page for the logged user
     *
     * @param user user object with user parameters.
     * @return string representation of page url;
     */
    private String getStartPage(User user) {
        switch (user.getRole()) {
            case "user":
                return USER_PAGE;
            case "admin":
                return ADMIN_PAGE;
            default:
                return MAIN_PAGE;
        }
    }

    /**
     * @param user      user object with params
     * @param startPage page to display after user login.
     * @param request   current http request.
     * @return CommandResult with start page.
     */
    private CommandResult showStartPage(User user, String startPage, HttpServletRequest request) {

        request.getSession(true).setAttribute("user", user);
        request.getSession(true).setAttribute("role", user.getRole());
        request.getSession(true).setAttribute("url", startPage);
        return new CommandResult(CommandResult.ResponseType.REDIRECT, startPage);
    }
}

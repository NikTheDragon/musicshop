package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.validator.FieldValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Class for checking user's login and password and if they are ok user will be logged to system.
 *
 * @param login    user's login from input field on jsp page.
 * @param password user's password from input field on jsp page.
 * @return CommandResult object with response type (forward|redirect) and page to show.
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
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            String loginValidateResult = FieldValidator.validateLogPasField(login);
            String passwordValidateResult = FieldValidator.validateLogPasField(password);

            if (Boolean.parseBoolean(loginValidateResult) && Boolean.parseBoolean(passwordValidateResult)) {
                LOGGER.debug("Attempt to login user with login={}, and password={}", login, password);

                User user = receiver.loginUser(login, password);

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

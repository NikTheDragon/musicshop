package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class LoginUser implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUser.class);
    private final static String MAIN_PAGE = PageStore.MAIN_PAGE.getPageName();
    private final static String USER_PAGE = PageStore.USER_PAGE.getPageName();
    private final static String ADMIN_PAGE = PageStore.ADMIN_PAGE.getPageName();
    private UserReceiver receiver;

    public LoginUser(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            LOGGER.debug("Login= {} and password= {}", login, password);

            User user = receiver.loginUser(login, password);

            if (user != null) {
                String page;

                switch (user.getRole()) {
                    case "user":
                        page = MAIN_PAGE;
                        break;
                    case "admin":
                        page = ADMIN_PAGE;
                        break;
                    default:
                        page = MAIN_PAGE;
                }

                request.getSession(true).setAttribute("user", user);
                request.getSession(true).setAttribute("role", user.getRole());
                request.getSession(true).setAttribute("url", page);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, page);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, MAIN_PAGE);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in LoginUser", e);
        }
    }
}

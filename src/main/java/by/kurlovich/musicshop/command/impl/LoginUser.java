package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.pagefactory.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class LoginUser implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUser.class);

    public LoginUser() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String page = PageStore.MAIN_PAGE.getPageName();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        LOGGER.debug("Login= {} and password= {}", login, password);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }
}

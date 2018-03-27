package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;
import by.kurlovich.musicshop.pagefactory.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginUser implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginUser.class);
    private static final LoginUser instance = new LoginUser();

    public static LoginUser getInstance() {
        return instance;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        String page = PageStore.MAIN_PAGE.getPageName();
        String login = requestContent.getRequest().getParameter("login");
        String password = requestContent.getRequest().getParameter("password");

        LOGGER.debug("Login= {} and password= {}", login, password);
        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }
}

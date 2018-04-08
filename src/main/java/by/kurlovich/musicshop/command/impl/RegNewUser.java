package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RegNewUser implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegNewUser.class);
    private static final String REG_PAGE = PageStore.REG_PAGE.getPageName();
    private static final String REGISTRATION_COMPLETE = PageStore.REGISTRATION_COMPLETE.getPageName();
    private UserReceiver receiver;

    public RegNewUser(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        Map<String, String> messageMap;
        User user = new User();

        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("e-mail"));
        user.setRole("user");
        user.setStatus("active");

        request.setAttribute("user", user);

        try {
            messageMap = receiver.validateUser(user);

            if (!Boolean.parseBoolean(messageMap.get("validate"))) {
                request.setAttribute("message", messageMap);
                return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
            }

            if (receiver.addNewUser(user)) {
                request.getSession(true).setAttribute("url", REGISTRATION_COMPLETE);
                request.getSession(true).setAttribute("user", user);
                return new CommandResult(CommandResult.ResponseType.REDIRECT, REGISTRATION_COMPLETE);
            } else {
                messageMap.put("loginMessage", "login in use");
                request.setAttribute("message", messageMap);
                return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
            }
        } catch (ReceiverException e) {
            throw new CommandException("Exception in RegNewUser", e);
        }
    }
}

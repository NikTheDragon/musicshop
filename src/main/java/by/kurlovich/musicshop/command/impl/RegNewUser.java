package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.FieldValidator;
import by.kurlovich.musicshop.validator.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RegNewUser implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegNewUser.class);
    private static final String REG_PAGE = PageStore.REG_PAGE.getPageName();
    private static final String MESSAGE_PAGE = PageStore.MESSAGE_PAGE.getPageName();
    private UserReceiver receiver;
    private ObjectValidator objectValidator = new ObjectValidator();

    public RegNewUser(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        Map<String, String> messageMap = new HashMap<>();
        User user = new User();

        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("e-mail"));

        objectValidator.validateUser(messageMap, user);

        request.setAttribute("user", user);

        if (!objectValidator.validateUser(messageMap, user)) {
            request.setAttribute("message", messageMap);
            return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
        }

        try {
            if (receiver.addNewUser(user)) {
                request.setAttribute("message", "registration complete.");
                request.getSession(true).setAttribute("url", MESSAGE_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, MESSAGE_PAGE);
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

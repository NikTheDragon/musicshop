package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.FieldValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class RegNewUser implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegNewUser.class);
    private static final String REG_PAGE = PageStore.REG_PAGE.getPageName();
    private static final String MESSAGE_PAGE = PageStore.MESSAGE_PAGE.getPageName();
    private UserReceiver receiver;
    private FieldValidator validator = new FieldValidator();

    public RegNewUser(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        boolean isOk = true;
        String message;

        User user = new User();

        user.setName(request.getParameter("name"));
        message = validator.validate(request.getParameter("name"));
        isOk = Boolean.parseBoolean(message);
        request.setAttribute("nameMessage", message);

        user.setSurname(request.getParameter("surname"));
        message = validator.validate(request.getParameter("surname"));
        isOk = Boolean.parseBoolean(message);
        request.setAttribute("surnameMessage", message);

        user.setLogin(request.getParameter("login"));
        message = validator.validate(request.getParameter("login"));
        isOk = Boolean.parseBoolean(message);
        request.setAttribute("loginMessage", message);

        user.setPassword(request.getParameter("password"));
        message = validator.validate(request.getParameter("password"));
        isOk = Boolean.parseBoolean(message);
        request.setAttribute("passwordMessage", message);

        user.setEmail(request.getParameter("e-mail"));
        message = validator.validate(request.getParameter("e-mail"));
        isOk = Boolean.parseBoolean(message);
        request.setAttribute("emailMessage", message);

        request.setAttribute("user", user);

        if (!isOk) {
            return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
        }

        try {
            if (receiver.addNewUser(user)) {
                request.setAttribute("message", "registration complete.");
                request.getSession(true).setAttribute("url", MESSAGE_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, MESSAGE_PAGE);
            } else {
                request.setAttribute("loginMessage", "login in use.");
                return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
            }
        } catch (ReceiverException e) {
            throw new CommandException("Exception in RegNewUser", e);
        }
    }
}

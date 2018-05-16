package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.ObjectValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class RegNewUserCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(RegNewUserCommand.class);
    private static final String REG_PAGE = PageStore.REG_PAGE.getPageName();
    private static final String REGISTRATION_COMPLETE = PageStore.REGISTRATION_COMPLETE.getPageName();
    private UserReceiver receiver;

    public RegNewUserCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> userValidationMessages = ObjectValidator.validateUser(requestMap);

            User user = ObjectCreator.createUser(requestMap);
            request.setAttribute("user", user);
            LOGGER.debug("get user: {}", user);

            if (Boolean.parseBoolean(userValidationMessages.get("isPassedValidation"))) {

                if (receiver.addNewUser(user)) {
                    User currentUser = receiver.loginUser(user.getLogin(), user.getPassword());

                    request.getSession(true).setAttribute("url", REGISTRATION_COMPLETE);
                    request.getSession(true).setAttribute("user", currentUser);
                    request.getSession(true).setAttribute("role", currentUser.getRole());
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, REGISTRATION_COMPLETE);
                }

                userValidationMessages.put("loginResult", "login");
            }

            request.setAttribute("messages", userValidationMessages);
            return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in RegNewUserCommand.\n" + e, e);
        }
    }
}

package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.util.validator.ObjectValidator;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UpdatePersonalInfoCommand implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePersonalInfoCommand.class);
    private static final String PERSONAL_PAGE = PageStore.PERSONAL_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final UserReceiver receiver;

    public UpdatePersonalInfoCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin", "user");
            User sessionUser = (User) request.getSession(true).getAttribute("user");
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> userValidationMessages = ObjectValidator.validateUser(requestMap);

            if (AccessValidator.validate(accessRoles, sessionUser.getRole())) {
                if (Boolean.parseBoolean(userValidationMessages.get("isPassedValidation"))) {
                    User currentUser = ObjectCreator.createUser(requestMap);

                    if (receiver.updateUser(currentUser)) {
                        request.setAttribute("commandResult", "done");
                        request.getSession(true).setAttribute("user", currentUser);
                        request.getSession(true).setAttribute("url", PERSONAL_PAGE);
                        return new CommandResult(CommandResult.ResponseType.FORWARD, PERSONAL_PAGE);

                    } else {
                        request.setAttribute("message", "login");
                        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
                    }

                } else {
                    request.setAttribute("messages", userValidationMessages);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, PERSONAL_PAGE);
                }
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdatePersonalInfoCommand.\n" + e, e);
        }
    }
}

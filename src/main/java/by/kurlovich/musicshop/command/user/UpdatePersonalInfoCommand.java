package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.util.validator.ObjectValidator;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class UpdatePersonalInfoCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePersonalInfoCommand.class);
    private static final String PERSONAL_PAGE = PageStore.PERSONAL_PAGE.getPageName();
    private final UserReceiver receiver;

    public UpdatePersonalInfoCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("update personal info command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> userValidationMessages = ObjectValidator.validateUser(requestMap);

            if (!Boolean.parseBoolean(userValidationMessages.get("isPassedValidation"))) {
                return createUnvalidatedResult(request, userValidationMessages, PERSONAL_PAGE);
            }

            User currentUser = ObjectCreator.createUser(requestMap);

            if (!receiver.updateUser(currentUser)) {
                return createFailedResult(request, "login");
            }

            request.setAttribute("commandResult", "done");
            request.getSession(true).setAttribute("user", currentUser);
            request.getSession(true).setAttribute("url", PERSONAL_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, PERSONAL_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdatePersonalInfoCommand.\n" + e, e);
        }
    }
}

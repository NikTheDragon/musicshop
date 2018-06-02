package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import by.kurlovich.musicshop.util.validator.FieldValidator;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UpdatePasswordCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdatePasswordCommand.class);
    private static final String PERSONAL_PAGE = PageStore.PERSONAL_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final UserReceiver receiver;

    public UpdatePasswordCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("update password command executed.");
            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            User sessionUser = getCurrentUser(request);

            Map<String, String[]> requestMap = request.getParameterMap();

            String[] oldPassword = requestMap.get("submit_old_password");
            String[] newPassword = requestMap.get("submit_new_password");

            String oldPasswordResult = FieldValidator.validateLogPasField(oldPassword);
            String newPasswordResult = FieldValidator.validateLogPasField(newPassword);

            if (Boolean.parseBoolean(oldPasswordResult) && Boolean.parseBoolean(newPasswordResult)) {

                if (receiver.updatePassword(oldPassword[0], newPassword[0], sessionUser)) {
                    List<User> currentUser = receiver.getSpecifiedUsers(sessionUser.getId());

                    request.setAttribute("commandResult", "done");
                    request.getSession(true).setAttribute("user", currentUser.get(0));
                    request.getSession(true).setAttribute("url", PERSONAL_PAGE);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, PERSONAL_PAGE);

                } else {
                    request.setAttribute("oldPasswordResult", "mismatch");
                    request.setAttribute("newPasswordResult", "mismatch");
                    return new CommandResult(CommandResult.ResponseType.FORWARD, PERSONAL_PAGE);
                }

            } else {
                request.setAttribute("oldPasswordResult", oldPasswordResult);
                request.setAttribute("newPasswordResult", newPasswordResult);
                return new CommandResult(CommandResult.ResponseType.FORWARD, PERSONAL_PAGE);
            }

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdatePasswordCommand.\n" + e, e);
        }
    }
}

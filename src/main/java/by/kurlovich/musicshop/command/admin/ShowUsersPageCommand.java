package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
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

public class ShowUsersPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowUsersPageCommand.class);
    private static final String SHOW_USERS_PAGE = PageStore.SHOW_USERS_PAGE.getPageName();
    private final UserReceiver receiver;

    public ShowUsersPageCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show users command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            List<User> allUsers = receiver.getAllUsers();

            request.getSession(true).setAttribute("allUsers", allUsers);

            return createOKResult(request, SHOW_USERS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowUsersPage.\n" + e, e);
        }
    }
}

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

public class ShowEditUserPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEditUserPageCommand.class);
    private static final String SHOW_EDIT_USER_PAGE = PageStore.SHOW_EDIT_USER_PAGE.getPageName();
    private final UserReceiver receiver;

    public ShowEditUserPageCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show edit users command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            String userId = request.getParameter("submit_user_id");
            List<User> allUsers = receiver.getSpecifiedUsers(userId);

            request.getSession(true).setAttribute("userInfo", allUsers.get(0));

            return createOKResult(request, SHOW_EDIT_USER_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditUserPageCommand.\n" + e, e);
        }
    }
}

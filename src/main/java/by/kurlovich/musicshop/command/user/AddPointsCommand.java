package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class AddPointsCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPointManagementPageCommand.class);
    private static final String POINTS_PAGE = PageStore.POINTS_PAGE.getPageName();
    private UserReceiver receiver;

    public AddPointsCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            if (!isAuthorised(request)){
                return createAccessDeniedResult(request);
            }
            User currentUser = getCurrentUser(request);
            Tariff tariff = getTariffSubmitted(request);
            if (addUserPoints(tariff, currentUser)) {
                return createOKResult(request, POINTS_PAGE);
            } else {
                return createUpdateFailedResult(request);
            }

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AddPointsCommand.\n" + e, e);
        }
    }

    protected Tariff getTariffSubmitted(HttpServletRequest request) {
        return Tariff.fromString(request.getParameter("submit_tariff"));
    }

    private boolean addUserPoints(Tariff tariff, User user) throws ReceiverException {
        if (tariff != null){
            user.setPoints(user.getPoints() + tariff.getPoints());
        } else {
            LOGGER.warn("tariff is null for user: {}", user);
        }
        return receiver.updateUser(user);
    }
}

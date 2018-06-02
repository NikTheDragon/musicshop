package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowMyMixesCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyTrackCommand.class);
    private static final String SHOW_MY_MIXES = PageStore.SHOW_MY_MIXES.getPageName();
    private UserReceiver receiver;

    public ShowMyMixesCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show my mixes command executed.");
            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            User currentUser = getCurrentUser(request);

            List<Mix> userMixes = receiver.getUserOwnedMixes(currentUser.getId());
            userMixes.sort(Comparator.comparing(Mix::getName));

            request.getSession(true).setAttribute("mixList", userMixes);
            request.getSession(true).setAttribute("url", SHOW_MY_MIXES);

            return createOKResult(request, SHOW_MY_MIXES);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowMyMixesCommand.\n" + e, e);
        }
    }
}

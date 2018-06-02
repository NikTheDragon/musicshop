package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowMyTracksCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyTrackCommand.class);
    private static final String SHOW_MY_TRACKS = PageStore.SHOW_MY_TRACKS.getPageName();
    private UserReceiver receiver;

    public ShowMyTracksCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show my tracks command executed.");
            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            User currentUser = getCurrentUser(request);

            List<Track> userTracks = receiver.getUserOwnedTracks(currentUser.getId());
            userTracks.sort(Comparator.comparing(Track::getAuthor));

            request.getSession(true).setAttribute("trackList", userTracks);
            request.getSession(true).setAttribute("url", SHOW_MY_TRACKS);

            return createOKResult(request, SHOW_MY_TRACKS);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowMyTracksCommand.\n" + e, e);
        }
    }
}

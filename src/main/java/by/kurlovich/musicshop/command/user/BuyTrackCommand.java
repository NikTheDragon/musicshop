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

public class BuyTrackCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyTrackCommand.class);
    private static final String SHOW_TRACKS_PAGE = PageStore.SHOW_TRACKS_PAGE.getPageName();
    private UserReceiver receiver;

    public BuyTrackCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("by track command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            User currentUser = getCurrentUser(request);

            String trackId = request.getParameter("track_id");
            int trackPrice = Integer.parseInt(request.getParameter("track_price"));

            String result = receiver.buyTrack(currentUser, trackId, trackPrice);

            if (!Boolean.parseBoolean(result)) {
                return createFailedResult(request, result);
            }

            List<Track> allTracks = receiver.getAllTracksWithOwner(currentUser.getId());
            allTracks.sort(Comparator.comparing(Track::getAuthor));

            request.getSession(true).setAttribute("user", currentUser);
            request.getSession(true).setAttribute("trackList", allTracks);

            return createOKResult(request, SHOW_TRACKS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in BuyTrackCommand.\n" + e, e);
        }
    }
}

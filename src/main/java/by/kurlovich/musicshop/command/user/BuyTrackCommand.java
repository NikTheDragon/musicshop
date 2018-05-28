package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BuyTrackCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(BuyTrackCommand.class);
    private final static String SHOW_TRACKS_PAGE = PageStore.SHOW_TRACKS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private UserReceiver receiver;

    public BuyTrackCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin", "user");
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (AccessValidator.validate(accessRoles, currentUser.getRole())) {
                String trackId = request.getParameter("track_id");
                int trackPrice = Integer.parseInt(request.getParameter("track_price"));

                String result = receiver.buyTrack(currentUser, trackId, trackPrice);

                if (Boolean.parseBoolean(result)) {
                    List<Track> allTracks = receiver.getAllTracksWithOwner(currentUser.getId());
                    allTracks.sort(Comparator.comparing(Track::getAuthor));

                    request.getSession(true).setAttribute("user", currentUser);
                    request.getSession(true).setAttribute("trackList", allTracks);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_TRACKS_PAGE);
                }

                request.setAttribute("message", "result");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in BuyTrackCommand.\n" + e, e);
        }
    }
}

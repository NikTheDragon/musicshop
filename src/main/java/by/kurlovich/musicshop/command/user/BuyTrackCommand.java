package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.validator.AccessValidator;
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
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                User currentUser = (User) request.getSession(true).getAttribute("user");

                String trackId = request.getParameter("track_id");
                int trackPrice = Integer.parseInt(request.getParameter("track_price"));
                int userPoints = currentUser.getPoints();

                LOGGER.debug("User: {}, trying to buy track: {}", currentUser.getId(), trackId);

                if (trackPrice <= userPoints) {
                    receiver.buyTrack(currentUser.getId(), trackId);

                    userPoints -= trackPrice;
                    currentUser.setPoints(userPoints);

                    receiver.updateUser(currentUser);

                    List<Track> allTracks = (List<Track>) request.getSession(true).getAttribute("trackList");
                    allTracks.sort(Comparator.comparing(Track::getAuthor));

                    for (Track track : allTracks) {
                        if (track.getId().equals(trackId)) {
                            track.setOwnerId(currentUser.getId());
                        }
                    }

                    request.getSession(true).setAttribute("user", currentUser);
                    request.getSession(true).setAttribute("trackList", allTracks);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_TRACKS_PAGE);
                }

                request.setAttribute("message", "insufficient points");

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

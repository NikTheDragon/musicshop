package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Album;
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

public class BuyAlbumCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyAlbumCommand.class);
    private static final String SHOW_ALBUMS_PAGE = PageStore.SHOW_ALBUMS_PAGE.getPageName();
    private UserReceiver receiver;

    public BuyAlbumCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("by album command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            User currentUser = getCurrentUser(request);

            String albumId = request.getParameter("album_id");
            int albumPrice = Integer.parseInt(request.getParameter("album_price"));

            String result = receiver.buyAlbum(currentUser, albumId, albumPrice);

            if (!Boolean.parseBoolean(result)) {
                return createFailedResult(request, result);
            }

            List<Track> currentAlbumTracks = receiver.getAlbumTracksWithOwner(currentUser.getId(), albumId);
            buyAlbumTracks(currentAlbumTracks, currentUser);

            List<Album> allAlbums = receiver.getAllAlbumsWithOwner(currentUser.getId());
            allAlbums.sort(Comparator.comparing(Album::getName));

            request.getSession(true).setAttribute("user", currentUser);
            request.getSession(true).setAttribute("albumList", allAlbums);

            return createOKResult(request, SHOW_ALBUMS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in BuyAlbumCommand.\n" + e, e);
        }
    }

    protected void buyAlbumTracks(List<Track> currentAlbumTracks, User currentUser) throws CommandException {
        try {
            for (Track currentTrack : currentAlbumTracks) {
                if (currentTrack.getOwnerId() == null) {
                    receiver.buyTrack(currentUser, currentTrack.getId(), 0);
                }
            }
        } catch (ReceiverException e) {
            throw new CommandException("Exception in BuyAlbumCommand.\n" + e, e);
        }
    }
}

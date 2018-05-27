package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Album;
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

public class BuyAlbumCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(BuyAlbumCommand.class);
    private final static String SHOW_ALBUMS_PAGE = PageStore.SHOW_ALBUMS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private UserReceiver receiver;

    public BuyAlbumCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin", "user");
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (AccessValidator.validate(accessRoles, currentUser.getRole())) {
                String albumId = request.getParameter("album_id");
                int albumPrice = Integer.parseInt(request.getParameter("album_price"));
                int userPoints = currentUser.getPoints();
                String currentUserId = currentUser.getId();

                LOGGER.debug("User: {}, trying to buy album: {}", currentUserId, albumId);

                if (albumPrice <= userPoints) {
                    receiver.buyAlbum(currentUserId, albumId);

                    userPoints -= albumPrice;
                    currentUser.setPoints(userPoints);

                    receiver.updateUser(currentUser);

                    List<Track> currentAlbumTracks = receiver.getAlbumTracksWithOwner(currentUserId, albumId);

                    for (Track currentTrack : currentAlbumTracks) {
                        if (currentTrack.getOwnerId() == null) {
                            receiver.buyTrack(currentUserId, currentTrack.getId());
                        }
                    }

                    List<Album> allAlbums = receiver.getAllAlbumsWithOwner(currentUserId);
                    allAlbums.sort(Comparator.comparing(Album::getName));

                    request.getSession(true).setAttribute("user", currentUser);
                    request.getSession(true).setAttribute("albumList", allAlbums);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, SHOW_ALBUMS_PAGE);
                }

                request.setAttribute("message", "insufficient points");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in BuyAlbumCommand.\n" + e, e);
        }
    }
}

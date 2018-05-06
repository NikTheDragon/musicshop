package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.EntityReceiver;
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

public class BuyAlbumCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(BuyAlbumCommand.class);
    private final static String SHOW_ALBUMS_PAGE = PageStore.SHOW_ALBUMS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private UserReceiver userReceiver;
    private EntityReceiver trackReceiver;

    public BuyAlbumCommand(UserReceiver receiver) {
        this.userReceiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin", "user");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                User currentUser = (User) request.getSession(true).getAttribute("user");

                String albumId = request.getParameter("album_id");
                int albumPrice = Integer.parseInt(request.getParameter("album_price"));
                int userPoints = currentUser.getPoints();
                String currentUserId = currentUser.getId();

                LOGGER.debug("User: {}, trying to buy album: {}", currentUserId, albumId);

                if (albumPrice <= userPoints) {
                    userReceiver.buyAlbum(currentUserId, albumId);

                    userPoints -= albumPrice;
                    currentUser.setPoints(userPoints);

                    userReceiver.updateUser(currentUser);

                    List<Track> currentAlbumTracks = userReceiver.getAlbumTracksWithOwner(currentUserId, albumId);

                    for (Track currentTrack : currentAlbumTracks) {
                        LOGGER.debug("current track id:{}.", currentTrack.getOwnerId());

                        if (currentTrack.getOwnerId() == null) {
                            userReceiver.buyTrack(currentUserId, currentTrack.getId());
                        }
                    }

                    List<Album> allAlbums = userReceiver.getAllAlbumsWithOwner(currentUserId);
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

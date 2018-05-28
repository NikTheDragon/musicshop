package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.util.GetCurrentUserId;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowAlbumContentCommand implements Command {
    private final static String SHOW_ALBUM_CONTENT_PAGE = PageStore.SHOW_ALBUM_CONTENT_PAGE.getPageName();
    private UserReceiver userReceiver;
    private EntityReceiver albumReceiver;

    public ShowAlbumContentCommand(UserReceiver userReceiver, EntityReceiver albumReceiver) {
        this.userReceiver = userReceiver;
        this.albumReceiver = albumReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {

            User currentUser = (User) request.getSession(true).getAttribute("user");

            String currentUserId = GetCurrentUserId.get(currentUser);
            String currentAlbumId = request.getParameter("album_id");
            List<Album> specifiedAlbums = albumReceiver.getSpecifiedEntities(currentAlbumId);

            if (!specifiedAlbums.isEmpty()) {
                List<Track> currentAlbumTracks = userReceiver.getAlbumTracksWithOwner(currentUserId, currentAlbumId);

                request.getSession(true).setAttribute("contentList", currentAlbumTracks);
                request.getSession(true).setAttribute("currentAlbum", specifiedAlbums.get(0));
            }

            request.getSession(true).setAttribute("url", SHOW_ALBUM_CONTENT_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_ALBUM_CONTENT_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowAlbumContentCommand.\n" + e, e);
        }
    }
}

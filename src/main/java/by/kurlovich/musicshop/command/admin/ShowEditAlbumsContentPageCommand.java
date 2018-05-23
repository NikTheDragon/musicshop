package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ShowEditAlbumsContentPageCommand implements Command {
    private final static String EDIT_ALBUMS_CONTENT_PAGE = PageStore.EDIT_ALBUMS_CONTENT_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private EntityReceiver contentReceiver;
    private EntityReceiver trackReceiver;
    private EntityReceiver albumReceiver;

    public ShowEditAlbumsContentPageCommand(EntityReceiver contentReceiver, EntityReceiver trackReceiver, EntityReceiver albumReceiver) {
        this.contentReceiver = contentReceiver;
        this.trackReceiver = trackReceiver;
        this.albumReceiver = albumReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                String albumId = request.getParameter("submit_id");

                List<Album> idSpecifiedAlbums = albumReceiver.getSpecifiedEntities(albumId);
                Album currentAlbum = idSpecifiedAlbums.get(0);

                List<Content> currentAlbumContent = contentReceiver.getSpecifiedEntities(albumId);
                List<Track> currentAlbumAuthorTracks = trackReceiver.getSpecifiedEntities(currentAlbum.getAuthor());

                request.getSession(true).setAttribute("currentAlbum", currentAlbum);
                request.getSession(true).setAttribute("authorTracks", currentAlbumAuthorTracks);
                request.getSession(true).setAttribute("albumContent", currentAlbumContent);
                request.getSession(true).setAttribute("url", EDIT_ALBUMS_CONTENT_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_ALBUMS_CONTENT_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditAlbumsContentPage.\n" + e, e);
        }
    }
}

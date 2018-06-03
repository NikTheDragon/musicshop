package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowEditAlbumsContentPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEditAlbumsContentPageCommand.class);
    private static final String EDIT_ALBUMS_CONTENT_PAGE = PageStore.EDIT_ALBUMS_CONTENT_PAGE.getPageName();
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
            LOGGER.info("show edit albums content command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            String albumId = request.getParameter("submit_id");

            List<Album> idSpecifiedAlbums = albumReceiver.getSpecifiedEntities(albumId);
            Album currentAlbum = idSpecifiedAlbums.get(0);

            List<Content> currentAlbumContent = contentReceiver.getSpecifiedEntities(albumId);
            List<Track> currentAlbumAuthorTracks = trackReceiver.getSpecifiedEntities(currentAlbum.getAuthor());

            request.getSession(true).setAttribute("currentAlbum", currentAlbum);
            request.getSession(true).setAttribute("authorTracks", currentAlbumAuthorTracks);
            request.getSession(true).setAttribute("albumContent", currentAlbumContent);

            return createOKResult(request, EDIT_ALBUMS_CONTENT_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditAlbumsContentPage.\n" + e, e);
        }
    }
}

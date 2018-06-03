package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class DeleteTrackFromAlbumCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTrackFromAlbumCommand.class);
    private static final String EDIT_ALBUMS_CONTENT_PAGE = PageStore.EDIT_ALBUMS_CONTENT_PAGE.getPageName();
    private EntityReceiver receiver;

    public DeleteTrackFromAlbumCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("delete track from album command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            Content content = createContent(request);

            LOGGER.debug("trying to remove track: {} from album: {}.", content.getTrackId(), content.getEntityId());

            if (!receiver.deleteEntity(content)) {
                return createFailedResult(request, "undelete");
            }

            List<Content> currentAlbumContent = receiver.getSpecifiedEntities(content.getEntityId());
            currentAlbumContent.sort(Comparator.comparing(Content::getTrackName));

            request.getSession(true).setAttribute("albumContent", currentAlbumContent);

            return createOKResult(request, EDIT_ALBUMS_CONTENT_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteTrackFromAlbumCommand.\n" + e, e);
        }
    }

    private Content createContent(HttpServletRequest request) {
        Content content = new Content();

        content.setEntityId(request.getParameter("submit_album_id"));
        content.setTrackId(request.getParameter("submit_track_id"));

        return content;
    }
}

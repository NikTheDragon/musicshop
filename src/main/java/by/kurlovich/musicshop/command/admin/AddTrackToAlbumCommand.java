package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class AddTrackToAlbumCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddTrackToAlbumCommand.class);
    private final static String EDIT_ALBUMS_CONTENT_PAGE = PageStore.EDIT_ALBUMS_CONTENT_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private EntityReceiver receiver;

    public AddTrackToAlbumCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");
            List<String> accessRoles = Arrays.asList("admin");

            if (AccessValidator.validate(accessRoles, userRole)) {
                Content content = createContent(request);

                LOGGER.debug("Adding track:{} to album.", content.getTrackName());

                if (receiver.addNewEntity(content)) {
                    List<Content> currentAlbumContent = receiver.getSpecifiedEntities(content.getEntityId());

                    request.getSession(true).setAttribute("albumContent", currentAlbumContent);
                    request.getSession(true).setAttribute("url", EDIT_ALBUMS_CONTENT_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_ALBUMS_CONTENT_PAGE);
                }

                request.setAttribute("message", "exists");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AddTrackToAlbumCommand.\n" + e, e);
        }
    }

    private Content createContent(HttpServletRequest request) {
        Content content = new Content();

        content.setEntityId(request.getParameter("submit_album_id"));
        content.setTrackId(request.getParameter("submit_track_id"));
        content.setTrackName(request.getParameter("submit_track"));
        content.setAuthorName(request.getParameter("submit_author"));
        content.setStatus("active");

        return content;
    }
}

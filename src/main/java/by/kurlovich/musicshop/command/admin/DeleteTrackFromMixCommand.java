package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.store.PageStore;

import by.kurlovich.musicshop.validator.AccessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DeleteTrackFromMixCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(AddTrackToMixCommand.class);
    private final static String EDIT_MIXES_CONTENT_PAGE = PageStore.EDIT_MIXES_CONTENT_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private EntityReceiver receiver;

    public DeleteTrackFromMixCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        AccessValidator accessValidator = new AccessValidator();
        List<String> accessRoles = Arrays.asList("admin");

        try {
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (accessValidator.validate(accessRoles, userRole)) {
                Content content = new Content();

                content.setEntityId(request.getParameter("submit_mix_id"));
                content.setTrackId(request.getParameter("submit_track_id"));

                LOGGER.debug("Deleting track {} from mix {}.", content.getTrackId(), content.getEntityId());

                if (receiver.deleteEntity(content)) {
                    List<Content> contentList = receiver.getAllEntities();
                    contentList.sort(Comparator.comparing(Content::getTrackName));

                    request.getSession(true).setAttribute("contentList", contentList);
                    request.getSession(true).setAttribute("url", EDIT_MIXES_CONTENT_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_MIXES_CONTENT_PAGE);
                }

                request.setAttribute("message", "undelete");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteTrackFromMixCommand.\n" + e, e);
        }
    }
}

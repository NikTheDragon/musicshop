package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.receiver.ContentReceiver;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class AddTrackToMixCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddTrackToMixCommand.class);
    private static final String EDIT_MIXES_CONTENT_PAGE = PageStore.EDIT_MIXES_CONTENT_PAGE.getPageName();
    private ContentReceiver receiver;

    public AddTrackToMixCommand(ContentReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("add track to mix command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            Content content = createContent(request);

            LOGGER.debug("trying to add track: {} to mix: {}.", content.getTrackName(), content.getEntityId());

            if (!receiver.addNewEntity(content)) {
                return createFailedResult(request, "exists");
            }

            List<Content> currentMixContent = receiver.getSpecifiedEntities(content.getEntityId());
            currentMixContent.sort(Comparator.comparing(Content::getTrackName));

            request.getSession(true).setAttribute("contentList", currentMixContent);

            return createOKResult(request, EDIT_MIXES_CONTENT_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AddTrackToMixCommand.\n" + e, e);
        }
    }

    private Content createContent(HttpServletRequest request) {
        Content content = new Content();

        content.setEntityId(request.getParameter("submit_mix_id"));
        content.setTrackName(request.getParameter("submit_track"));
        content.setAuthorName(request.getParameter("submit_author"));
        content.setStatus("active");

        return content;
    }
}

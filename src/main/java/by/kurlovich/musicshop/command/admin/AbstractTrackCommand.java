package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class AbstractTrackCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTrackCommand.class);
    private static final String EDIT_TRACKS_PAGE = PageStore.EDIT_TRACKS_PAGE.getPageName();
    private EntityReceiver receiver;

    AbstractTrackCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("track command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> trackValidationMessages = ObjectValidator.validateTrack(requestMap);

            if (!Boolean.parseBoolean(trackValidationMessages.get("isPassedValidation"))) {
                return createUnvalidatedResult(request, trackValidationMessages, EDIT_TRACKS_PAGE);
            }

            Track track = ObjectCreator.createTrack(requestMap);

            if (!doCommand(track)) {
                return createFailedResult(request, "unable");
            }

            List<Track> allTracks = receiver.getAllEntities();
            allTracks.sort(Comparator.comparing(Track::getAuthor));

            request.getSession(true).setAttribute("trackList", allTracks);

            return createOKResult(request, EDIT_TRACKS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateTrackCommand.\n" + e, e);
        }
    }

    public abstract boolean doCommand(Track track) throws CommandException;
}

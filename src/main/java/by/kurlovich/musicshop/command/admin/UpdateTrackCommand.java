package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.AccessValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class UpdateTrackCommand implements Command {
    private final static String EDIT_TRACKS_PAGE = PageStore.EDIT_TRACKS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateTrackCommand.class);
    private EntityReceiver receiver;

    public UpdateTrackCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");
            Map<String, String[]> requestMap = request.getParameterMap();

            if (AccessValidator.validate(accessRoles, userRole)) {
                Track track = ObjectCreator.createTrack(requestMap);

                LOGGER.debug("Updating track: {}", track.getName());

                if (receiver.updateEntity(track)) {
                    List<Track> allTracks = receiver.getAllEntities();
                    allTracks.sort(Comparator.comparing(Track::getAuthor));

                    request.getSession(true).setAttribute("trackList", allTracks);
                    request.getSession(true).setAttribute("url", EDIT_TRACKS_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_TRACKS_PAGE);
                }

                request.setAttribute("message", "undelete");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateTrackCommand.\n" + e, e);
        }
    }
}

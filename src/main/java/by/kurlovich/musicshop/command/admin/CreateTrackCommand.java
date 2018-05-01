package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
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

public class CreateTrackCommand implements Command {
    private final static String EDIT_TRACKS_PAGE = PageStore.EDIT_TRACKS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(CreateTrackCommand.class);
    private EntityReceiver receiver;

    public CreateTrackCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                Track track = createTrack(request);

                LOGGER.debug("Creating track: {}", track.getName());

                if (receiver.addNewEntity(track)) {
                    List<Track> allTracks = receiver.getAllEntities();
                    allTracks.sort(Comparator.comparing(Track::getAuthor));

                    request.getSession(true).setAttribute("trackList", allTracks);
                    request.getSession(true).setAttribute("url", EDIT_TRACKS_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_TRACKS_PAGE);
                }

                request.setAttribute("message", "exists");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateTrackCommand.\n" + e, e);
        }
    }

    private Track createTrack(HttpServletRequest request) {
        Track track = new Track();

        track.setName(request.getParameter("submit_name"));
        track.setAuthor(request.getParameter("submit_author"));
        track.setGenre(request.getParameter("submit_genre"));
        track.setYear(request.getParameter("submit_year"));
        track.setLength(request.getParameter("submit_length"));
        track.setStatus("active");

        return track;
    }
}

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.TrackReceiver;
import by.kurlovich.musicshop.validator.AccessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class CreateTrack implements Command {
    private final static String EDIT_TRACKS_PAGE = PageStore.EDIT_TRACKS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(CreateTrack.class);
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private TrackReceiver receiver;

    public CreateTrack(TrackReceiver receiver) {

        this.receiver = receiver;
    }


    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");
            request.setAttribute("nocommand", "Access denied!");

            if (accessValidator.validate(accessRoles, userRole)) {
                Track track = new Track();
                track.setName(request.getParameter("submit_name"));
                track.setAuthor(request.getParameter("submit_author"));
                track.setGenre(request.getParameter("submit_genre"));
                track.setYear(request.getParameter("submit_year"));
                track.setLength(request.getParameter("submit_length"));
                track.setStatus("active");

                LOGGER.debug("Creating track: {}", track);

                if (receiver.addNewTrack(track)) {
                    List<Track> genres = receiver.getAllTracks();
                    request.getSession(true).setAttribute("genres", genres);
                    request.getSession(true).setAttribute("url", EDIT_TRACKS_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_TRACKS_PAGE);
                }

                request.setAttribute("nocommand", "Can't add new genre.");
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in Create Genre.", e);
        }
    }
}

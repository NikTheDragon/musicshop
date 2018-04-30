package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.impl.TrackReceiverImpl;
import by.kurlovich.musicshop.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubmitTrackCommand implements Command {
    private final static String EDIT_MIXES_CONTENT_PAGE = PageStore.EDIT_MIXES_CONTENT_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private AccessValidator accessValidator = new AccessValidator();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            Set<String> names = new HashSet<>();
            Set<String> authors = new HashSet<>();
            Set<String> genres = new HashSet<>();

            if (accessValidator.validate(accessRoles, userRole)) {
                String author = request.getParameter("submit_author");
                String genre = request.getParameter("submit_genre");
                String mixId = request.getParameter("submit_mix_id");
                EntityReceiver trackReceiver = new TrackReceiverImpl();

                List<Track> trackList = trackReceiver.getAllEntities();

                for (Track track : trackList) {
                    if (track.getAuthor().equals(author)) {
                        names.add(track.getName());
                    }
                    if (track.getGenre().equals(genre)) {
                        authors.add(track.getAuthor());
                    }
                    if (genre.equals("*")) {
                        authors.add(track.getAuthor());
                    }
                    genres.add(track.getGenre());
                }

                request.setAttribute("currentAuthor", author);
                request.setAttribute("currentGenre", genre);
                request.getSession(true).setAttribute("namesSet", names);
                request.getSession(true).setAttribute("authorsSet", authors);
                request.getSession(true).setAttribute("genresSet", genres);
                request.getSession(true).setAttribute("trackList", trackList);
                request.getSession(true).setAttribute("url", EDIT_MIXES_CONTENT_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_MIXES_CONTENT_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditMixesContentPageCommand.\n" + e, e);
        }
    }
}

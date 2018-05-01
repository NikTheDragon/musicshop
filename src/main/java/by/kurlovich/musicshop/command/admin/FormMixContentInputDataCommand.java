package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormMixContentInputDataCommand implements Command {
    private final static String EDIT_MIXES_CONTENT_PAGE = PageStore.EDIT_MIXES_CONTENT_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    EntityReceiver trackReceiver;

    public FormMixContentInputDataCommand (EntityReceiver trackReceiver) {
        this.trackReceiver = trackReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                String selectedAuthor = request.getParameter("submit_author");
                String selectedGenre = request.getParameter("submit_genre");

                Set<String> uniqueTrackNames = new HashSet<>();
                Set<String> uniqueAuthors = new HashSet<>();
                Set<String> uniqueGenres = new HashSet<>();

                List<Track> allTracks = trackReceiver.getAllEntities();

                for (Track track : allTracks) {
                    if (track.getAuthor().equals(selectedAuthor)) {
                        uniqueTrackNames.add(track.getName());
                    }
                    if (track.getGenre().equals(selectedGenre)) {
                        uniqueAuthors.add(track.getAuthor());
                    }
                    if (selectedGenre.equals("*")) {
                        uniqueAuthors.add(track.getAuthor());
                    }
                    uniqueGenres.add(track.getGenre());
                }

                request.setAttribute("currentAuthor", selectedAuthor);
                request.setAttribute("currentGenre", selectedGenre);
                request.getSession(true).setAttribute("namesSet", uniqueTrackNames);
                request.getSession(true).setAttribute("authorsSet", uniqueAuthors);
                request.getSession(true).setAttribute("genresSet", uniqueGenres);
                request.getSession(true).setAttribute("trackList", allTracks);
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

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FormMixContentInputDataCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(FormMixContentInputDataCommand.class);
    private static final String EDIT_MIXES_CONTENT_PAGE = PageStore.EDIT_MIXES_CONTENT_PAGE.getPageName();
    EntityReceiver trackReceiver;

    public FormMixContentInputDataCommand(EntityReceiver trackReceiver) {
        this.trackReceiver = trackReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("form mix content input data command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

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
            request.setAttribute("namesSet", uniqueTrackNames);
            request.setAttribute("authorsSet", uniqueAuthors);
            request.setAttribute("genresSet", uniqueGenres);
            request.getSession(true).setAttribute("trackList", allTracks);
            request.getSession(true).setAttribute("url", EDIT_MIXES_CONTENT_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_MIXES_CONTENT_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditMixesContentPageCommand.\n" + e, e);
        }
    }
}

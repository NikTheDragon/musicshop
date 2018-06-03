package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowEditTracksPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEditTracksPageCommand.class);
    private static final String EDIT_TRACKS_PAGE = PageStore.EDIT_TRACKS_PAGE.getPageName();
    private EntityReceiver trackReceiver;
    private EntityReceiver genreReceiver;
    private EntityReceiver authorReceiver;

    public ShowEditTracksPageCommand(EntityReceiver trackReceiver, EntityReceiver genreReceiver, EntityReceiver authorReceiver) {
        this.trackReceiver = trackReceiver;
        this.genreReceiver = genreReceiver;
        this.authorReceiver = authorReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show edit tracks command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            List<Genre> allGenres = genreReceiver.getAllEntities();
            allGenres.sort(Comparator.comparing(Genre::getName));

            List<Track> allTracks = trackReceiver.getAllEntities();
            allTracks.sort(Comparator.comparing(Track::getAuthor));

            List<Author> allAuthors = authorReceiver.getAllEntities();
            allAuthors.sort(Comparator.comparing(Author::getName));

            request.getSession(true).setAttribute("authorList", allAuthors);
            request.getSession(true).setAttribute("genreList", allGenres);
            request.getSession(true).setAttribute("trackList", allTracks);

            return createOKResult(request, EDIT_TRACKS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditTracksPageCommand.\n" + e, e);
        }

    }
}

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.util.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShowEditTracksPageCommand implements Command {
    private static final String EDIT_TRACKS_PAGE = PageStore.EDIT_TRACKS_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
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
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                List<Genre> allGenres = genreReceiver.getAllEntities();
                allGenres.sort(Comparator.comparing(Genre::getName));

                List<Track> allTracks = trackReceiver.getAllEntities();
                allTracks.sort(Comparator.comparing(Track::getAuthor));

                List<Author> allAuthors = authorReceiver.getAllEntities();
                allAuthors.sort(Comparator.comparing(Author::getName));

                request.getSession(true).setAttribute("authorList", allAuthors);
                request.getSession(true).setAttribute("genreList", allGenres);
                request.getSession(true).setAttribute("trackList", allTracks);
                request.getSession(true).setAttribute("url", EDIT_TRACKS_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_TRACKS_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditTracksPageCommand.\n"+e, e);
        }

    }
}

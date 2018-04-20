package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.AuthorReceiver;
import by.kurlovich.musicshop.receiver.GenreReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.TrackReceiver;
import by.kurlovich.musicshop.receiver.impl.AuthorReceiverImpl;
import by.kurlovich.musicshop.receiver.impl.GenreReceiverImpl;
import by.kurlovich.musicshop.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShowEditTracksPage implements Command {
    private final static String EDIT_TRACKS_PAGE = PageStore.EDIT_TRACKS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private TrackReceiver receiver;

    public ShowEditTracksPage(TrackReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (accessValidator.validate(accessRoles, userRole)) {
                GenreReceiver genreReceiver = new GenreReceiverImpl();
                AuthorReceiver authorReceiver = new AuthorReceiverImpl();

                List<Genre> genres = genreReceiver.getAllGenres();
                genres.sort(Comparator.comparing(Genre::getName));

                List<Track> trackList = receiver.getAllTracks();
                List<Author> authorList = authorReceiver.getAllAuthors();
                authorList.sort(Comparator.comparing(Author::getName));

                request.getSession(true).setAttribute("authors", authorList);
                request.getSession(true).setAttribute("genres", genres);
                request.getSession(true).setAttribute("trackList", trackList);
                request.getSession(true).setAttribute("url", EDIT_TRACKS_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_TRACKS_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in Show Edit Tracks Page.\n"+e, e);
        }

    }
}

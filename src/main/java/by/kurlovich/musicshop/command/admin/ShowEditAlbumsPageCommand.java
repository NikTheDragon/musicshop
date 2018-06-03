package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowEditAlbumsPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEditAlbumsPageCommand.class);
    private static final String EDIT_ALBUMS_PAGE = PageStore.EDIT_ALBUMS_PAGE.getPageName();
    private EntityReceiver albumReceiver;
    private EntityReceiver genreReceiver;
    private EntityReceiver authorReceiver;

    public ShowEditAlbumsPageCommand(EntityReceiver albumReceiver, EntityReceiver genreReceiver, EntityReceiver authorReceiver) {
        this.albumReceiver = albumReceiver;
        this.genreReceiver = genreReceiver;
        this.authorReceiver = authorReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show edit albums command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            List<Genre> allGenres = genreReceiver.getAllEntities();
            allGenres.sort(Comparator.comparing(Genre::getName));

            List<Author> allAuthors = authorReceiver.getAllEntities();
            allAuthors.sort(Comparator.comparing(Author::getName));

            List<Album> allAlbums = albumReceiver.getAllEntities();
            allAlbums.sort(Comparator.comparing(Album::getName));

            request.getSession(true).setAttribute("genreList", allGenres);
            request.getSession(true).setAttribute("authorList", allAuthors);
            request.getSession(true).setAttribute("albumList", allAlbums);

            return createOKResult(request, EDIT_ALBUMS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditAlbumsPageCommand.\n" + e, e);
        }
    }
}

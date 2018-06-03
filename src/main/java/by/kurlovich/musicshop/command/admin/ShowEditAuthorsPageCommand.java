package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
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

public class ShowEditAuthorsPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEditAuthorsPageCommand.class);
    private static final String EDIT_AUTHORS_PAGE = PageStore.EDIT_AUTHORS_PAGE.getPageName();
    private EntityReceiver authorReceiver;
    private EntityReceiver genreReceiver;

    public ShowEditAuthorsPageCommand(EntityReceiver authorReceiver, EntityReceiver genreReceiver) {
        this.authorReceiver = authorReceiver;
        this.genreReceiver = genreReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show edit authors command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            List<Genre> allGenres = genreReceiver.getAllEntities();
            allGenres.sort(Comparator.comparing(Genre::getName));

            List<Author> allAuthors = authorReceiver.getAllEntities();
            allAuthors.sort(Comparator.comparing(Author::getName));

            request.getSession(true).setAttribute("genreList", allGenres);
            request.getSession(true).setAttribute("authorList", allAuthors);

            return createOKResult(request, EDIT_AUTHORS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditAuthorsPageCommand.\n" + e, e);
        }
    }
}

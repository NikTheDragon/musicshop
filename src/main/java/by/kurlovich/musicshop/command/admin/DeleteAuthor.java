package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.AuthorReceiver;
import by.kurlovich.musicshop.receiver.GenreReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.impl.GenreReceiverImpl;
import by.kurlovich.musicshop.validator.AccessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DeleteAuthor implements Command {
    private final static String EDIT_AUTHORS_PAGE = PageStore.EDIT_AUTHORS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteAuthor.class);
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private AuthorReceiver receiver;

    public DeleteAuthor(AuthorReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");
            request.setAttribute("message", "denied");
            GenreReceiver genreReceiver = new GenreReceiverImpl();

            if (accessValidator.validate(accessRoles, userRole)) {
                Author author = new Author();
                author.setName(request.getParameter("submit_name"));
                author.setGenre(request.getParameter("submit_genre"));
                author.setType(request.getParameter("submit_type"));

                if (receiver.deleteAuthor(author)) {
                    List<Genre> genres = genreReceiver.getAllGenres();
                    genres.sort(Comparator.comparing(Genre::getName));

                    List<Author> authorList = receiver.getAllAuthors();
                    authorList.sort(Comparator.comparing(Author::getName));

                    request.getSession(true).setAttribute("authorList", authorList);
                    request.getSession(true).setAttribute("genres", genres);
                    request.getSession(true).setAttribute("url", EDIT_AUTHORS_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_AUTHORS_PAGE);
                }

                request.setAttribute("message", "undelete");
            }
            request.getSession(true).setAttribute("url", ERROR_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in Delete Author.", e);
        }
    }
}

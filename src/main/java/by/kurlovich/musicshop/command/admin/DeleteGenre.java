package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.GenreReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.AccessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DeleteGenre implements Command {
    private final static String EDIT_GENRES_PAGE = PageStore.EDIT_GENRES_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteGenre.class);
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private GenreReceiver receiver;

    public DeleteGenre(GenreReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");
            request.setAttribute("nocommand", "Access denied!");

            if (accessValidator.validate(accessRoles, userRole)) {
                Genre genre = new Genre();
                genre.setName(request.getParameter("submit_name"));

                if (receiver.deleteGenre(genre)) {
                    List<Genre> genres = receiver.getAllGenres();
                    genres.sort(Comparator.comparing(Genre::getName));

                    request.getSession(true).setAttribute("genres", genres);
                    request.getSession(true).setAttribute("url", EDIT_GENRES_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_GENRES_PAGE);
                }

                request.setAttribute("nocommand", "Can't delete genre.");
            }
            request.getSession(true).setAttribute("url", ERROR_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in Delete Genre.", e);
        }
    }
}
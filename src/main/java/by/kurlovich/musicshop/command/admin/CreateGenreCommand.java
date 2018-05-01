package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.AccessValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CreateGenreCommand implements Command {
    private final static String EDIT_GENRES_PAGE = PageStore.EDIT_GENRES_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(CreateGenreCommand.class);
    private EntityReceiver receiver;

    public CreateGenreCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                Genre genre = createGenre(request);

                LOGGER.debug("Creating genre: {}", genre.getName());

                if (receiver.addNewEntity(genre)) {
                    List<Genre> allGenres = receiver.getAllEntities();
                    allGenres.sort(Comparator.comparing(Genre::getName));

                    request.getSession(true).setAttribute("genreList", allGenres);
                    request.getSession(true).setAttribute("url", EDIT_GENRES_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_GENRES_PAGE);
                }

                request.setAttribute("message", "exists");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateGenreCommand.\n" + e, e);
        }
    }

    private Genre createGenre (HttpServletRequest request) {
        Genre genre = new Genre();

        genre.setName(request.getParameter("submit_name"));
        genre.setStatus("active");

        return genre;
    }
}

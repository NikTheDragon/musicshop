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

public class DeleteGenreCommand implements Command {
    private final static String EDIT_GENRES_PAGE = PageStore.EDIT_GENRES_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteGenreCommand.class);
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private EntityReceiver receiver;

    public DeleteGenreCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (accessValidator.validate(accessRoles, userRole)) {
                Genre genre = new Genre();
                genre.setName(request.getParameter("submit_name"));

                LOGGER.debug("Deleting genre: {}", genre.getName());

                if (receiver.deleteEntity(genre)) {
                    List<Genre> genreList = receiver.getAllEntities();
                    genreList.sort(Comparator.comparing(Genre::getName));

                    request.getSession(true).setAttribute("genreList", genreList);
                    request.getSession(true).setAttribute("url", EDIT_GENRES_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_GENRES_PAGE);
                }

                request.setAttribute("message", "undelete");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteGenreCommand.\n" + e, e);
        }
    }
}

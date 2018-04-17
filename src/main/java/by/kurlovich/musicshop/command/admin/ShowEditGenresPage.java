package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.GenreReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowEditGenresPage implements Command {
    private final static String EDIT_GENRES_PAGE = PageStore.EDIT_GENRES_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private GenreReceiver receiver;

    public ShowEditGenresPage(GenreReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (accessValidator.validate(accessRoles, userRole)) {
                List<Genre> genres = receiver.getAllGenres();

                request.getSession(true).setAttribute("genres", genres);
                request.getSession(true).setAttribute("url", EDIT_GENRES_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_GENRES_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("nocommand", "Access denied!");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in Show Edit Genres Page.", e);
        }
    }
}

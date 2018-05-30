package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.util.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShowEditGenresPageCommand implements Command {
    private static final String EDIT_GENRES_PAGE = PageStore.EDIT_GENRES_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private EntityReceiver receiver;

    public ShowEditGenresPageCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                List<Genre> allGenres = receiver.getAllEntities();
                allGenres.sort(Comparator.comparing(Genre::getName));

                request.getSession(true).setAttribute("genreList", allGenres);
                request.getSession(true).setAttribute("url", EDIT_GENRES_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_GENRES_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditGenresPageCommand.\n" + e, e);
        }
    }
}

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.util.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShowEditMixesPageCommand implements Command {
    private final static String EDIT_MIXES_PAGE = PageStore.EDIT_MIXES_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private EntityReceiver mixReceiver;
    private EntityReceiver genreReceiver;

    public ShowEditMixesPageCommand(EntityReceiver mixReceiver, EntityReceiver genreReceiver) {
        this.mixReceiver = mixReceiver;
        this.genreReceiver = genreReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                List<Genre> allGenres = genreReceiver.getAllEntities();
                allGenres.sort(Comparator.comparing(Genre::getName));

                List<Mix> allMixes = mixReceiver.getAllEntities();
                allMixes.sort(Comparator.comparing(Mix::getName));

                request.getSession(true).setAttribute("genreList", allGenres);
                request.getSession(true).setAttribute("mixList", allMixes);
                request.getSession(true).setAttribute("url", EDIT_MIXES_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_MIXES_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditMixesPageCommand.\n" + e, e);
        }
    }
}

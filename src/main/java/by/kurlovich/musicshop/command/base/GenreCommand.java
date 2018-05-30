package by.kurlovich.musicshop.command.base;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import by.kurlovich.musicshop.util.validator.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class GenreCommand implements Command {
    private static final String EDIT_GENRES_PAGE = PageStore.EDIT_GENRES_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private static final Logger LOGGER = LoggerFactory.getLogger(GenreCommand.class);
    private EntityReceiver receiver;

    public GenreCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> genreValidationMessages = ObjectValidator.validateGenre(requestMap);

            if (AccessValidator.validate(accessRoles, userRole)) {
                if (Boolean.parseBoolean(genreValidationMessages.get("isPassedValidation"))) {
                    Genre genre = ObjectCreator.createGenre(requestMap);

                    LOGGER.debug("Command: {}, found", requestMap.get("command")[0]);

                    if (doCommand(genre)) {
                        List<Genre> allGenres = receiver.getAllEntities();
                        allGenres.sort(Comparator.comparing(Genre::getName));

                        request.getSession(true).setAttribute("genreList", allGenres);
                        request.getSession(true).setAttribute("url", EDIT_GENRES_PAGE);
                        return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_GENRES_PAGE);
                    }

                    request.setAttribute("message", "unable");

                } else {
                    request.setAttribute("messages", genreValidationMessages);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_GENRES_PAGE);
                }

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in GenreCommand.\n" + e, e);
        }
    }

    public abstract boolean doCommand(Genre genre) throws CommandException;
}

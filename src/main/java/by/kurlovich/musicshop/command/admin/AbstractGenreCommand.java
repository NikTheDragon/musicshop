package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class AbstractGenreCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractGenreCommand.class);
    private static final String EDIT_GENRES_PAGE = PageStore.EDIT_GENRES_PAGE.getPageName();
    private EntityReceiver receiver;

    public AbstractGenreCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("genre command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> genreValidationMessages = ObjectValidator.validateGenre(requestMap);

            if (!Boolean.parseBoolean(genreValidationMessages.get("isPassedValidation"))) {
                return createUnvalidatedResult(request, genreValidationMessages, EDIT_GENRES_PAGE);
            }

            Genre genre = ObjectCreator.createGenre(requestMap);

            if (!doCommand(genre)) {
                return createFailedResult(request, "unable");
            }

            List<Genre> allGenres = receiver.getAllEntities();
            allGenres.sort(Comparator.comparing(Genre::getName));

            request.getSession(true).setAttribute("genreList", allGenres);

            return createOKResult(request, EDIT_GENRES_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AbstractGenreCommand.\n" + e, e);
        }
    }

    public abstract boolean doCommand(Genre genre) throws CommandException;
}

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowEditGenresPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEditGenresPageCommand.class);
    private static final String EDIT_GENRES_PAGE = PageStore.EDIT_GENRES_PAGE.getPageName();
    private EntityReceiver receiver;

    public ShowEditGenresPageCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show edit genres command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            List<Genre> allGenres = receiver.getAllEntities();
            allGenres.sort(Comparator.comparing(Genre::getName));

            request.getSession(true).setAttribute("genreList", allGenres);

            return createOKResult(request, EDIT_GENRES_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditGenresPageCommand.\n" + e, e);
        }
    }
}

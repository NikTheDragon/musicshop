package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowEditMixesPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEditMixesPageCommand.class);
    private static final String EDIT_MIXES_PAGE = PageStore.EDIT_MIXES_PAGE.getPageName();
    private EntityReceiver mixReceiver;
    private EntityReceiver genreReceiver;

    public ShowEditMixesPageCommand(EntityReceiver mixReceiver, EntityReceiver genreReceiver) {
        this.mixReceiver = mixReceiver;
        this.genreReceiver = genreReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show edit mixes command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            List<Genre> allGenres = genreReceiver.getAllEntities();
            allGenres.sort(Comparator.comparing(Genre::getName));

            List<Mix> allMixes = mixReceiver.getAllEntities();
            allMixes.sort(Comparator.comparing(Mix::getName));

            request.getSession(true).setAttribute("genreList", allGenres);
            request.getSession(true).setAttribute("mixList", allMixes);

            return createOKResult(request, EDIT_MIXES_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditMixesPageCommand.\n" + e, e);
        }
    }
}

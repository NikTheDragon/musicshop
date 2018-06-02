package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Album;
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

public abstract class AbstractAlbumCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAlbumCommand.class);
    private static final String EDIT_ALBUMS_PAGE = PageStore.EDIT_ALBUMS_PAGE.getPageName();
    private EntityReceiver receiver;

    AbstractAlbumCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("album command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> albumValidationMessages = ObjectValidator.validateAlbum(requestMap);

            if (!Boolean.parseBoolean(albumValidationMessages.get("isPassedValidation"))) {
                return createUnvalidatedResult(request, albumValidationMessages, EDIT_ALBUMS_PAGE);
            }

            Album album = ObjectCreator.createAlbum(requestMap);

            if (!doCommand(album)) {
                return createFailedResult(request, "unable");
            }

            List<Album> allAlbums = receiver.getAllEntities();
            allAlbums.sort(Comparator.comparing(Album::getName));

            request.getSession(true).setAttribute("albumList", allAlbums);

            return createOKResult(request, EDIT_ALBUMS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AbstractAlbumCommand.\n" + e, e);
        }
    }

    public abstract boolean doCommand(Album album) throws CommandException;
}

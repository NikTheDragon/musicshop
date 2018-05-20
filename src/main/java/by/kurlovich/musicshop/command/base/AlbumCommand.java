package by.kurlovich.musicshop.command.base;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.validator.AccessValidator;
import by.kurlovich.musicshop.validator.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class AlbumCommand implements Command {
    private final static String EDIT_ALBUMS_PAGE = PageStore.EDIT_ALBUMS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(AlbumCommand.class);
    private EntityReceiver receiver;

    public AlbumCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> albumValidationMessages = ObjectValidator.validateAlbum(requestMap);

            if (AccessValidator.validate(accessRoles, userRole)) {
                if (Boolean.parseBoolean(albumValidationMessages.get("isPassedValidation"))) {
                    Album album = ObjectCreator.createAlbum(requestMap);

                    LOGGER.debug("Command: {}, found", requestMap.get("command")[0]);

                    if (doCommand(album)) {
                        List<Album> allAlbums = receiver.getAllEntities();
                        allAlbums.sort(Comparator.comparing(Album::getName));

                        request.getSession(true).setAttribute("albumList", allAlbums);
                        request.getSession(true).setAttribute("url", EDIT_ALBUMS_PAGE);
                        return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_ALBUMS_PAGE);
                    }

                    request.setAttribute("message", "unable");

                } else {
                    request.setAttribute("messages", albumValidationMessages);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_ALBUMS_PAGE);
                }

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AlbumCommand.\n" + e, e);
        }
    }

    public abstract boolean doCommand(Album album) throws CommandException;
}

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Album;
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

public class UpdateAlbumCommand implements Command {
    private final static String EDIT_ALBUMS_PAGE = PageStore.EDIT_ALBUMS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateAlbumCommand.class);
    private EntityReceiver receiver;

    public UpdateAlbumCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                Album album = createAlbum(request);

                LOGGER.debug("Updating album: {}", album.getName());

                if (receiver.updateEntity(album)) {
                    List<Album> allAlbums = receiver.getAllEntities();
                    allAlbums.sort(Comparator.comparing(Album::getName));

                    request.getSession(true).setAttribute("albumList", allAlbums);
                    request.getSession(true).setAttribute("url", EDIT_ALBUMS_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_ALBUMS_PAGE);
                }

                request.setAttribute("message", "unupdate");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateAlbumCommand.\n" + e, e);
        }
    }

    private Album createAlbum(HttpServletRequest request) {
        Album album = new Album();

        album.setId(request.getParameter("submit_id"));
        album.setName(request.getParameter("submit_name"));
        album.setGenre(request.getParameter("submit_genre"));
        album.setAuthor(request.getParameter("submit_author"));
        album.setYear(Integer.parseInt(request.getParameter("submit_year")));

        return album;
    }
}

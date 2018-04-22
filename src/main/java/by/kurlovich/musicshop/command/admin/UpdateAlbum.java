package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.AccessValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class UpdateAlbum implements Command {
    private final static String EDIT_ALBUMS_PAGE = PageStore.EDIT_ALBUMS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateAlbum.class);
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private EntityReceiver receiver;

    public UpdateAlbum(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");
            request.setAttribute("message", "denied");

            if (accessValidator.validate(accessRoles, userRole)) {
                Album album = new Album();
                album.setId(request.getParameter("submit_id"));
                album.setName(request.getParameter("submit_name"));
                album.setGenre(request.getParameter("submit_genre"));
                album.setAuthor(request.getParameter("submit_author"));
                album.setYear(Integer.parseInt(request.getParameter("submit_year")));

                LOGGER.debug("Updating album: {}", album.getName());

                if (receiver.updateEntity(album)) {
                    List<Album> albumList = receiver.getAllEntities();
                    albumList.sort(Comparator.comparing(Album::getName));

                    request.getSession(true).setAttribute("albumList", albumList);
                    request.getSession(true).setAttribute("url", EDIT_ALBUMS_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_ALBUMS_PAGE);
                }

                request.setAttribute("message", "unupdate");
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateAlbum.\n" + e, e);
        }
    }
}

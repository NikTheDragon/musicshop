package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.impl.AuthorReceiverImpl;
import by.kurlovich.musicshop.receiver.impl.GenreReceiverImpl;
import by.kurlovich.musicshop.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShowEditAlbumsPage implements Command {
    private final static String EDIT_ALBUMS_PAGE = PageStore.EDIT_ALBUMS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private EntityReceiver receiver;

    public ShowEditAlbumsPage(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (accessValidator.validate(accessRoles, userRole)) {
                EntityReceiver genreReceiver = new GenreReceiverImpl();
                EntityReceiver authorReceiver = new AuthorReceiverImpl();

                List<Genre> genreList = genreReceiver.getAllEntities();
                genreList.sort(Comparator.comparing(Genre::getName));

                List<Author> authorList = authorReceiver.getAllEntities();
                authorList.sort(Comparator.comparing(Author::getName));

                List<Album> albumList = receiver.getAllEntities();
                albumList.sort(Comparator.comparing(Album::getName));

                request.getSession(true).setAttribute("genreList", genreList);
                request.getSession(true).setAttribute("authorList", authorList);
                request.getSession(true).setAttribute("albumList", albumList);
                request.getSession(true).setAttribute("url", EDIT_ALBUMS_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_ALBUMS_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditAlbumsPage.\n" + e, e);
        }
    }
}

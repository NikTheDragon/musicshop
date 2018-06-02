package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowMyAlbumsCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyTrackCommand.class);
    private static final String SHOW_MY_ALBUMS = PageStore.SHOW_MY_ALBUMS.getPageName();
    private UserReceiver receiver;

    public ShowMyAlbumsCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("show my albums command executed.");
            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            User currentUser = getCurrentUser(request);

            List<Album> userAlbums = receiver.getUserOwnedAlbums(currentUser.getId());
            userAlbums.sort(Comparator.comparing(Album::getName));

            request.getSession(true).setAttribute("albumList", userAlbums);
            request.getSession(true).setAttribute("url", SHOW_MY_ALBUMS);

            return createOKResult(request, SHOW_MY_ALBUMS);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowMyAlbumsCommand.\n" + e, e);
        }
    }
}

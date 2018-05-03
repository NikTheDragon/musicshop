package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowAllAlbumsCommand implements Command {
    private final static String SHOW_ALBUMS_PAGE = PageStore.SHOW_ALBUMS_PAGE.getPageName();
    private EntityReceiver receiver;

    public ShowAllAlbumsCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<Album> allAlbums = receiver.getAllEntities();
            allAlbums.sort(Comparator.comparing(Album::getName));

            request.getSession(true).setAttribute("albumList", allAlbums);
            request.getSession(true).setAttribute("url", SHOW_ALBUMS_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_ALBUMS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowAllAlbumsCommand.\n" + e, e);
        }

    }
}

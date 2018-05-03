package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowAllTracksCommand implements Command {
    private final static String SHOW_TRACKS_PAGE = PageStore.SHOW_TRACKS_PAGE.getPageName();
    private EntityReceiver receiver;

    public ShowAllTracksCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String userId = "0";
            User user = (User) request.getSession(true).getAttribute("user");

            if (user != null) {
                userId = user.getId();
            }

            List<Track> allTracks = receiver.getEntitiesWithOwner(userId);
            allTracks.sort(Comparator.comparing(Track::getAuthor));

            request.getSession(true).setAttribute("trackList", allTracks);
            request.getSession(true).setAttribute("url", SHOW_TRACKS_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_TRACKS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowAllTracksCommand.\n" + e, e);
        }

    }
}

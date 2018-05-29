package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.util.UserUtil;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowAllTracksCommand implements Command {
    private final static String SHOW_TRACKS_PAGE = PageStore.SHOW_TRACKS_PAGE.getPageName();
    private UserReceiver receiver;

    public ShowAllTracksCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {

            User currentUser = (User) request.getSession(true).getAttribute("user");

            String currentUserId = UserUtil.getId(currentUser);

            List<Track> allTracks = receiver.getAllTracksWithOwner(currentUserId);
            allTracks.sort(Comparator.comparing(Track::getAuthor));

            request.getSession(true).setAttribute("trackList", allTracks);
            request.getSession(true).setAttribute("url", SHOW_TRACKS_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_TRACKS_PAGE);


        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowAllTracksCommand.\n" + e, e);
        }

    }
}

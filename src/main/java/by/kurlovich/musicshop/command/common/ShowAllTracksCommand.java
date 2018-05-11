package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
            String currentUserId = "0";
            String pageToShow = (String) request.getParameter("pageToShow");

            if (pageToShow == null) {
                pageToShow = "1";
            }

            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (currentUser != null) {
                currentUserId = currentUser.getId();
            }

            List<Track> allTracks = receiver.getAllTracksWithOwner(currentUserId);
            allTracks.sort(Comparator.comparing(Track::getAuthor));

            int selectedPage = Integer.parseInt(pageToShow);
            int firstNote = (selectedPage - 1) * 15;
            int lastNote = (selectedPage * 15);
            int totalPages = allTracks.size() / 15 + 1;
            int firstActivePage = selectedPage - 10;
            int lastActivePage = selectedPage + 10;

            if (firstActivePage < 1) {
                firstActivePage = 1;
            }

            if (lastActivePage > totalPages) {
                lastActivePage = totalPages;
            }

            if (lastNote > allTracks.size()) {
                lastNote = allTracks.size();
            }

            List<Track> pageTracks = new ArrayList<>(allTracks.subList(firstNote, lastNote));

            request.setAttribute("firstActivePage", firstActivePage);
            request.setAttribute("lastActivePage", lastActivePage);
            request.setAttribute("totalPages", totalPages);
            request.getSession(true).setAttribute("trackList", pageTracks);
            request.getSession(true).setAttribute("url", SHOW_TRACKS_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_TRACKS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowAllTracksCommand.\n" + e, e);
        }

    }
}

package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.util.UserUtil;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.util.validator.ObjectValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SearchTracksCommand implements Command {
    private EntityReceiver receiver;

    public SearchTracksCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            User currentUser = (User) request.getSession(true).getAttribute("user");

            String currentUserId = UserUtil.getId(currentUser);
            String currentURI = request.getParameter("currentURI");

            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> searchValidationMessages = ObjectValidator.validateSearch(requestMap);

            if (Boolean.parseBoolean(searchValidationMessages.get("isPassedValidation"))) {
                SearchData sd = ObjectCreator.createSearchData(requestMap);

                List<Track> searchedTracks = receiver.getSearchedEntities(sd, currentUserId);
                searchedTracks.sort(Comparator.comparing(Track::getAuthor));

                request.getSession(true).setAttribute("trackList", searchedTracks);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, currentURI);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in SearchTracksCommand.\n" + e, e);
        }
    }
}

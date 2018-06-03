package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class BuyMixCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyMixCommand.class);
    private static final String SHOW_MIXES_PAGE = PageStore.SHOW_MIXES_PAGE.getPageName();
    private UserReceiver receiver;

    public BuyMixCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("by mix command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            User currentUser = getCurrentUser(request);

            String mixId = request.getParameter("mix_id");
            int mixPrice = Integer.parseInt(request.getParameter("mix_price"));

            String result = receiver.buyMix(currentUser, mixId, mixPrice);

            if (!Boolean.parseBoolean(result)) {
                return createFailedResult(request, result);
            }

            List<Track> currentMixTracks = receiver.getMixTracksWithOwner(currentUser.getId(), mixId);
            buyMixTracks(currentMixTracks, currentUser);

            List<Mix> allMixes = receiver.getAllMixesWithOwner(currentUser.getId());
            allMixes.sort(Comparator.comparing(Mix::getName));

            request.getSession(true).setAttribute("user", currentUser);
            request.getSession(true).setAttribute("mixList", allMixes);

            return createOKResult(request, SHOW_MIXES_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in BuyMixCommand.\n" + e, e);
        }
    }

    private void buyMixTracks(List<Track> currentMixTracks, User currentUser) throws CommandException {
        try {
            for (Track currentTrack : currentMixTracks) {
                if (currentTrack.getOwnerId() == null) {
                    receiver.buyTrack(currentUser, currentTrack.getId(), 0);
                }
            }
        } catch (ReceiverException e) {
            throw new CommandException("Exception in BuyMixCommand.\n" + e, e);
        }
    }
}

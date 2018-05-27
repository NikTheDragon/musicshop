package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BuyMixCommand implements Command {
    private final static Logger LOGGER = LoggerFactory.getLogger(BuyMixCommand.class);
    private final static String SHOW_MIXES_PAGE = PageStore.SHOW_MIXES_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private UserReceiver receiver;

    public BuyMixCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin", "user");
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (AccessValidator.validate(accessRoles, currentUser.getRole())) {
                String mixId = request.getParameter("mix_id");
                int mixPrice = Integer.parseInt(request.getParameter("mix_price"));
                int userPoints = currentUser.getPoints();
                String currentUserId = currentUser.getId();

                LOGGER.debug("User: {}, trying to buy mix: {}", currentUserId, mixId);

                if (mixPrice <= userPoints) {
                    receiver.buyMix(currentUserId, mixId);

                    userPoints -= mixPrice;
                    currentUser.setPoints(userPoints);

                    receiver.updateUser(currentUser);

                    List<Track> currentMixTracks = receiver.getMixTracksWithOwner(currentUserId, mixId);

                    for (Track currentTrack : currentMixTracks) {
                        if (currentTrack.getOwnerId() == null) {
                            receiver.buyTrack(currentUserId, currentTrack.getId());
                        }
                    }

                    List<Mix> allMixes = receiver.getAllMixesWithOwner(currentUserId);
                    allMixes.sort(Comparator.comparing(Mix::getName));

                    request.getSession(true).setAttribute("user", currentUser);
                    request.getSession(true).setAttribute("mixList", allMixes);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, SHOW_MIXES_PAGE);
                }

                request.setAttribute("message", "insufficient points");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in BuyMixCommand.\n" + e, e);
        }
    }
}

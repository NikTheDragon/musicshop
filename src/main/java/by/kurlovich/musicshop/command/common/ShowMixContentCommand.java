package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.web.pages.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMixContentCommand implements Command {
    private final static String SHOW_MIX_CONTENT_PAGE = PageStore.SHOW_MIX_CONTENT_PAGE.getPageName();
    private UserReceiver userReceiver;
    private EntityReceiver mixReceiver;

    public ShowMixContentCommand(UserReceiver userReceiver, EntityReceiver mixReceiver) {
        this.userReceiver = userReceiver;
        this.mixReceiver = mixReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String currentUserId = "0";
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (currentUser != null) {
                currentUserId = currentUser.getId();
            }

            String currentMixId = request.getParameter("mix_id");

            List<Mix> specifiedMixes = mixReceiver.getSpecifiedEntities(currentMixId);

            if (!specifiedMixes.isEmpty()) {
                List<Track> currentMixTracks = userReceiver.getMixTracksWithOwner(currentUserId, currentMixId);

                request.getSession(true).setAttribute("contentList", currentMixTracks);
                request.getSession(true).setAttribute("currentMix", specifiedMixes.get(0));
            }

            request.getSession(true).setAttribute("url", SHOW_MIX_CONTENT_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_MIX_CONTENT_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowMixContentCommand.\n" + e, e);
        }
    }
}

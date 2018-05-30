package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.util.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ShowEditMixesContentPageCommand implements Command {
    private static final String EDIT_MIXES_CONTENT_PAGE = PageStore.EDIT_MIXES_CONTENT_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private EntityReceiver contentReceiver;
    private EntityReceiver trackReceiver;
    private EntityReceiver mixReceiver;

    public ShowEditMixesContentPageCommand(EntityReceiver contentReceiver, EntityReceiver trackReceiver, EntityReceiver mixReceiver) {
        this.contentReceiver = contentReceiver;
        this.trackReceiver = trackReceiver;
        this.mixReceiver = mixReceiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                String mixId = request.getParameter("submit_id");

                Set<String> uniqueNames = new HashSet<>();
                Set<String> uniqueAuthors = new HashSet<>();
                Set<String> uniqueGenres = new HashSet<>();

                List<Track> allTracks = trackReceiver.getAllEntities();

                for (Track track : allTracks) {
                    uniqueNames.add(track.getName());
                    uniqueAuthors.add(track.getAuthor());
                    uniqueGenres.add(track.getGenre());
                }

                List<Mix> idSpecifiedMix = mixReceiver.getSpecifiedEntities(mixId);
                List<Content> currentMixContent = contentReceiver.getSpecifiedEntities(mixId);

                request.getSession(true).setAttribute("currentMix", idSpecifiedMix.get(0));
                request.getSession(true).setAttribute("namesSet", uniqueNames);
                request.getSession(true).setAttribute("authorsSet", uniqueAuthors);
                request.getSession(true).setAttribute("genresSet", uniqueGenres);
                request.getSession(true).setAttribute("contentList", currentMixContent);
                request.getSession(true).setAttribute("url", EDIT_MIXES_CONTENT_PAGE);
                return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_MIXES_CONTENT_PAGE);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditMixesContentPageCommand.\n" + e, e);
        }
    }
}

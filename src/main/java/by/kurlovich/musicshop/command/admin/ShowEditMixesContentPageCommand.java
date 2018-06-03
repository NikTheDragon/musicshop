package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.entity.Content;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ShowEditMixesContentPageCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowEditMixesContentPageCommand.class);
    private static final String EDIT_MIXES_CONTENT_PAGE = PageStore.EDIT_MIXES_CONTENT_PAGE.getPageName();
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
            LOGGER.info("show edit mixes content command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

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

            return createOKResult(request, EDIT_MIXES_CONTENT_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowEditMixesContentPageCommand.\n" + e, e);
        }
    }
}

package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.pagefactory.PageStore;

import javax.servlet.http.HttpServletRequest;

public class ShowBrowseTracksPage implements Command {
    private final static String BROWSE_TRACKS_PAGE = PageStore.BROWSE_TRACKS_PAGE.getPageName();

    public ShowBrowseTracksPage() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        request.getSession(true).setAttribute("url", BROWSE_TRACKS_PAGE);
        return new CommandResult(CommandResult.ResponseType.FORWARD, BROWSE_TRACKS_PAGE);
    }
}

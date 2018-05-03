package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;

public class BuyTrackCommand implements Command {
    private final static String SHOW_ALBUMS_PAGE = PageStore.SHOW_ALBUMS_PAGE.getPageName();
    private EntityReceiver receiver;

    public BuyTrackCommand (EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}

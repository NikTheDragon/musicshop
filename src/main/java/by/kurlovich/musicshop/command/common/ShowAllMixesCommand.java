package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.store.PageStore;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

public class ShowAllMixesCommand implements Command {
    private final static String SHOW_MIXES_PAGE = PageStore.SHOW_MIXES_PAGE.getPageName();
    private EntityReceiver receiver;

    public ShowAllMixesCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<Mix> allMixes = receiver.getAllEntities();
            allMixes.sort(Comparator.comparing(Mix::getName));

            request.getSession(true).setAttribute("mixList", allMixes);
            request.getSession(true).setAttribute("url", SHOW_MIXES_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_MIXES_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowAllMixesCommand.\n" + e, e);
        }

    }
}

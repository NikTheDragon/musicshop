package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShowMyMixesCommand implements Command {
    private final static String SHOW_MY_MIXES = PageStore.SHOW_MY_MIXES.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private UserReceiver receiver;

    public ShowMyMixesCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin", "user");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                User currentUser = (User) request.getSession(true).getAttribute("user");

                List<Mix> userMixes = receiver.getUserOwnedMixes(currentUser.getId());
                userMixes.sort(Comparator.comparing(Mix::getName));

                request.getSession(true).setAttribute("mixList", userMixes);
                request.getSession(true).setAttribute("url", SHOW_MY_MIXES);
                return new CommandResult(CommandResult.ResponseType.FORWARD, SHOW_MY_MIXES);
            }

            request.getSession(true).setAttribute("url", ERROR_PAGE);
            request.setAttribute("message", "denied");
            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in ShowMyMixesCommand.\n" + e, e);
        }
    }
}

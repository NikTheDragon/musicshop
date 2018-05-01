package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.store.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.AccessValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CreateMixCommand implements Command {
    private final static String EDIT_MIXES_PAGE = PageStore.EDIT_MIXES_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(CreateMixCommand.class);
    private EntityReceiver receiver;

    public CreateMixCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                Mix mix = createMix(request);

                LOGGER.debug("Creating mix: {}", mix.getName());

                if (receiver.addNewEntity(mix)) {
                    List<Mix> allMixes = receiver.getAllEntities();
                    allMixes.sort(Comparator.comparing(Mix::getName));

                    request.getSession(true).setAttribute("mixList", allMixes);
                    request.getSession(true).setAttribute("url", EDIT_MIXES_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_MIXES_PAGE);
                }

                request.setAttribute("message", "exists");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateMixCommand.\n" + e, e);
        }
    }

    private Mix createMix(HttpServletRequest request) {
        Mix mix = new Mix();

        mix.setName(request.getParameter("submit_name"));
        mix.setGenre(request.getParameter("submit_genre"));
        mix.setYear(request.getParameter("submit_year"));
        mix.setStatus("active");

        return mix;
    }
}

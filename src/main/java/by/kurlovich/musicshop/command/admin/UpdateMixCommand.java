package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.creator.ObjectCreator;
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
import java.util.Map;

public class UpdateMixCommand implements Command {
    private final static String EDIT_MIXES_PAGE = PageStore.EDIT_MIXES_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateMixCommand.class);
    private EntityReceiver receiver;

    public UpdateMixCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");
            Map<String, String[]> requestMap = request.getParameterMap();

            if (AccessValidator.validate(accessRoles, userRole)) {
                Mix mix = ObjectCreator.createMix(requestMap);

                LOGGER.debug("Updating mix: {}", mix.getName());

                if (receiver.updateEntity(mix)) {
                    List<Mix> allMixes = receiver.getAllEntities();
                    allMixes.sort(Comparator.comparing(Mix::getName));

                    request.getSession(true).setAttribute("mixList", allMixes);
                    request.getSession(true).setAttribute("url", EDIT_MIXES_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_MIXES_PAGE);
                }

                request.setAttribute("message", "unupdate");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateMixCommand.\n" + e, e);
        }
    }
}

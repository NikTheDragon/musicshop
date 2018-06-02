package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class AbstractMixCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMixCommand.class);
    private static final String EDIT_MIXES_PAGE = PageStore.EDIT_MIXES_PAGE.getPageName();
    private EntityReceiver receiver;

    AbstractMixCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("mix command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> mixValidationMessages = ObjectValidator.validateMix(requestMap);

            if (!Boolean.parseBoolean(mixValidationMessages.get("isPassedValidation"))) {
                return createUnvalidatedResult(request, mixValidationMessages, EDIT_MIXES_PAGE);
            }

            Mix mix = ObjectCreator.createMix(requestMap);

            LOGGER.debug("Command: {}, found", requestMap.get("command")[0]);

            if (!doCommand(mix)) {
                return createFailedResult(request, "unable");
            }

            List<Mix> allMixes = receiver.getAllEntities();
            allMixes.sort(Comparator.comparing(Mix::getName));

            request.getSession(true).setAttribute("mixList", allMixes);

            return createOKResult(request, EDIT_MIXES_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AbstractMixCommand.\n" + e, e);
        }
    }

    public abstract boolean doCommand(Mix mix) throws CommandException;
}

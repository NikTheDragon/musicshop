package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.web.pages.PageStore;
import by.kurlovich.musicshop.util.validator.AccessValidator;
import by.kurlovich.musicshop.util.validator.ObjectValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public abstract class AbstractAuthorCommand implements Command {
    private static final String EDIT_AUTHORS_PAGE = PageStore.EDIT_AUTHORS_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuthorCommand.class);
    private EntityReceiver receiver;

    public AbstractAuthorCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");
            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> authorValidationMessages = ObjectValidator.validateAuthor(requestMap);

            if (AccessValidator.validate(accessRoles, userRole)) {
                if (Boolean.parseBoolean(authorValidationMessages.get("isPassedValidation"))) {
                    Author author = ObjectCreator.createAuthor(requestMap);

                    LOGGER.debug("Command: {}, found", requestMap.get("command")[0]);

                    if (doCommand(author)) {
                        List<Author> allAuthors = receiver.getAllEntities();
                        allAuthors.sort(Comparator.comparing(Author::getName));

                        request.getSession(true).setAttribute("authorList", allAuthors);
                        request.getSession(true).setAttribute("url", EDIT_AUTHORS_PAGE);
                        return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_AUTHORS_PAGE);
                    }

                    request.setAttribute("message", "unable");

                } else {
                    request.setAttribute("messages", authorValidationMessages);
                    return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_AUTHORS_PAGE);
                }

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AbstractAuthorCommand.\n" + e, e);
        }
    }

    public abstract boolean doCommand(Author author) throws CommandException;

}

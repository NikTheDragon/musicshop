package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.util.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Author;
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

public abstract class AbstractAuthorCommand extends AbstractAdminCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAuthorCommand.class);
    private static final String EDIT_AUTHORS_PAGE = PageStore.EDIT_AUTHORS_PAGE.getPageName();
    private EntityReceiver receiver;

    AbstractAuthorCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            LOGGER.info("author command executed.");

            if (!isAuthorised(request)) {
                return createAccessDeniedResult(request);
            }

            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> authorValidationMessages = ObjectValidator.validateAuthor(requestMap);

            if (Boolean.parseBoolean(authorValidationMessages.get("isPassedValidation"))) {
                return createUnvalidatedResult(request, authorValidationMessages, EDIT_AUTHORS_PAGE);
            }

            Author author = ObjectCreator.createAuthor(requestMap);

            if (!doCommand(author)) {
                return createFailedResult(request, "unable");
            }

            List<Author> allAuthors = receiver.getAllEntities();
            allAuthors.sort(Comparator.comparing(Author::getName));

            request.getSession(true).setAttribute("authorList", allAuthors);

            return createOKResult(request, EDIT_AUTHORS_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in AbstractAuthorCommand.\n" + e, e);
        }
    }

    public abstract boolean doCommand(Author author) throws CommandException;

}

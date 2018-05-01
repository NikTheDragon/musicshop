package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Author;
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

public class DeleteAuthorCommand implements Command {
    private final static String EDIT_AUTHORS_PAGE = PageStore.EDIT_AUTHORS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private final static Logger LOGGER = LoggerFactory.getLogger(DeleteAuthorCommand.class);
    private EntityReceiver receiver;

    public DeleteAuthorCommand(EntityReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            List<String> accessRoles = Arrays.asList("admin");
            String userRole = (String) request.getSession(true).getAttribute("role");

            if (AccessValidator.validate(accessRoles, userRole)) {
                Author author = createAuthor(request);

                LOGGER.debug("Deleting author: {}", author.getName());

                if (receiver.deleteEntity(author)) {
                    List<Author> allAuthors = receiver.getAllEntities();
                    allAuthors.sort(Comparator.comparing(Author::getName));

                    request.getSession(true).setAttribute("authorList", allAuthors);
                    request.getSession(true).setAttribute("url", EDIT_AUTHORS_PAGE);
                    return new CommandResult(CommandResult.ResponseType.REDIRECT, EDIT_AUTHORS_PAGE);
                }

                request.setAttribute("message", "undelete");

            } else {
                request.setAttribute("message", "denied");
                request.getSession(true).setAttribute("url", ERROR_PAGE);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteAuthorCommand." + e, e);
        }
    }

    private Author createAuthor(HttpServletRequest request) {
        Author author = new Author();

        author.setName(request.getParameter("submit_name"));
        author.setGenre(request.getParameter("submit_genre"));
        author.setType(request.getParameter("submit_type"));

        return author;
    }
}

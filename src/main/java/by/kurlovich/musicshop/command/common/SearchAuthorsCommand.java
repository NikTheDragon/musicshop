package by.kurlovich.musicshop.command.common;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.creator.ObjectCreator;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.entity.SearchData;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.validator.ObjectValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SearchAuthorsCommand implements Command {
    private EntityReceiver receiver;

    public SearchAuthorsCommand(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        try {
            String currentUserId = "0";
            User currentUser = (User) request.getSession(true).getAttribute("user");

            if (currentUser != null) {
                currentUserId = currentUser.getId();
            }

            String currentURI = request.getParameter("currentURI");

            Map<String, String[]> requestMap = request.getParameterMap();
            Map<String, String> searchValidationMessages = ObjectValidator.validateSearch(requestMap);

            if (Boolean.parseBoolean(searchValidationMessages.get("isPassedValidation"))) {
                SearchData sd = ObjectCreator.createSearchData(requestMap);

                List<Author> searchedAuthors = receiver.getSearchedEntities(sd, currentUserId);
                searchedAuthors.sort(Comparator.comparing(Author::getName));

                request.getSession(true).setAttribute("trackList", searchedAuthors);
            }

            return new CommandResult(CommandResult.ResponseType.FORWARD, currentURI);

        } catch (ReceiverException e) {
            throw new CommandException("Exception in SearchAuthorsCommand.\n" + e, e);
        }
    }
}

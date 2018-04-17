package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public class ShowEditTracksPage implements Command {
    private final static String EDIT_TRACKS_PAGE = PageStore.EDIT_TRACKS_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");

    public ShowEditTracksPage() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {
        String userRole = (String) request.getSession(true).getAttribute("role");

        if (accessValidator.validate(accessRoles, userRole)) {
            request.getSession(true).setAttribute("url", EDIT_TRACKS_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_TRACKS_PAGE);
        }

        request.getSession(true).setAttribute("url", ERROR_PAGE);
        request.setAttribute("nocommand", "Access denied!");
        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

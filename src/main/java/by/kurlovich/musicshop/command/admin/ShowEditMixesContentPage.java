package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.pages.PageStore;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.impl.GenreReceiverImpl;
import by.kurlovich.musicshop.validator.AccessValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ShowEditMixesContentPage implements Command {
    private final static String EDIT_MIXES_CONTENT_PAGE = PageStore.EDIT_MIXES_CONTENT_PAGE.getPageName();
    private final static String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();
    private AccessValidator accessValidator = new AccessValidator();
    private List<String> accessRoles = Arrays.asList("admin");
    private EntityReceiver receiver;

    public ShowEditMixesContentPage(EntityReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        String userRole = (String) request.getSession(true).getAttribute("role");

        if (accessValidator.validate(accessRoles, userRole)) {
            String mixId = request.getParameter("submit_id");

            List<Mix> mixList = (List<Mix>) request.getSession(true).getAttribute("mixList");

            for (Mix mix : mixList) {
                if (mix.getId().equals(mixId)) {
                    request.setAttribute("currentMix", mix);
                }
            }

            request.getSession(true).setAttribute("url", EDIT_MIXES_CONTENT_PAGE);
            return new CommandResult(CommandResult.ResponseType.FORWARD, EDIT_MIXES_CONTENT_PAGE);
        }

        request.getSession(true).setAttribute("url", ERROR_PAGE);
        request.setAttribute("message", "denied");
        return new CommandResult(CommandResult.ResponseType.FORWARD, ERROR_PAGE);
    }
}

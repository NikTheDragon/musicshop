package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ShowPointManagementPageCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPointManagementPageCommand.class);
    private static final String POINTS_PAGE = PageStore.POINTS_PAGE.getPageName();
    private static final String ERROR_PAGE = PageStore.ERROR_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {

        LOGGER.info("show points management command executed.");
        if (!isAuthorised(request)) {
            return createAccessDeniedResult(request);
        }

        request.getSession(true).setAttribute("url", POINTS_PAGE);

        return createOKResult(request, POINTS_PAGE);
    }
}

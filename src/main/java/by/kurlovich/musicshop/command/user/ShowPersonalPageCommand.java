package by.kurlovich.musicshop.command.user;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.web.CommandResult;
import by.kurlovich.musicshop.web.pages.PageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ShowPersonalPageCommand extends AbstractUserCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowPersonalPageCommand.class);
    private static final String PERSONAL_PAGE = PageStore.PERSONAL_PAGE.getPageName();

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {

        LOGGER.info("show personal page command executed.");
        if (!isAuthorised(request)) {
            return createAccessDeniedResult(request);
        }

        request.getSession(true).setAttribute("url", PERSONAL_PAGE);

        return createOKResult(request, PERSONAL_PAGE);
    }
}

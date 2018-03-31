package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import by.kurlovich.musicshop.receiver.impl.UserReceiverImpl;

import javax.servlet.http.HttpServletRequest;

public class RegNewUser implements Command {
    private static final String REG_PAGE = PageStore.REG_PAGE.getPageName();
    private static final String MESSAGE_PAGE = PageStore.MESSAGE_PAGE.getPageName();

    public static final RegNewUser instance = new RegNewUser();
    private UserReceiver receiver = UserReceiverImpl.getInstance();

    public static RegNewUser getInstance() {
        return instance;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) {

        User user = new User();
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("e-mail"));

        try {
            if (receiver.addNewUser(user)) {
                request.setAttribute("message", "registration complete.");
                request.getSession(true).setAttribute("url", MESSAGE_PAGE);
            }
        } catch (ReceiverException e) {
            //stub
        }

        return new CommandResult(CommandResult.ResponseType.FORWARD, REG_PAGE);
    }
}

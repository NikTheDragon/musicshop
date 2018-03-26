package by.kurlovich.musicshop.command.impl;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;
import by.kurlovich.musicshop.entity.User;
import by.kurlovich.musicshop.pagefactory.PageStore;
import by.kurlovich.musicshop.receiver.UserReceiver;
import by.kurlovich.musicshop.receiver.exception.ReceiverException;

public class RegNewUser implements Command {
    private String page = PageStore.REG_PAGE.getPageName();
    private UserReceiver receiver = UserReceiver.getInstance();
    public static final RegNewUser instance = new RegNewUser();

    public static RegNewUser getInstance() {
        return instance;
    }

    @Override
    public CommandResult execute(RequestContent requestContent) {
        User user = new User();
        user.setName(requestContent.getRequest().getParameter("name"));
        user.setSurname(requestContent.getRequest().getParameter("surname"));
        user.setLogin(requestContent.getRequest().getParameter("login"));
        user.setPassword(requestContent.getRequest().getParameter("password"));
        user.setEmail(requestContent.getRequest().getParameter("e-mail"));

        try {
            if (receiver.addNewUser(user)) {
                requestContent.setRequestParameters("message", "registration complete.");
                page = PageStore.MESSAGE_PAGE.getPageName();
                requestContent.getRequest().getSession(true).setAttribute("url", page);
            }
        } catch (ReceiverException e) {
            //stub
        }

        return new CommandResult(CommandResult.ResponseType.FORWARD, page);
    }
}

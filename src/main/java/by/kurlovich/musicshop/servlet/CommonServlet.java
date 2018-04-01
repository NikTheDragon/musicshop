package by.kurlovich.musicshop.servlet;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.command.CommandFactory;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;
import by.kurlovich.musicshop.pagefactory.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/common"})
public class CommonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonServlet.class);
    public static CommandFactory commandFactory = CommandFactory.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            LOGGER.debug("Command = {}", request.getParameter("command"));

            RequestContent requestContent = new RequestContent();
            requestContent.setRequest(request);
            requestContent.setRequestParameters("command", request.getParameter("command").toUpperCase());

            Command command = commandFactory.getCommand(request);
            CommandResult commandResult = null;

            commandResult = command.execute(request);

            RequestDispatcher dispatcher = request.getRequestDispatcher(commandResult.getPage());
            dispatcher.forward(request, response);

        } catch (CommandException e) {
            request.setAttribute("nocommand", e.getCause());
            RequestDispatcher dispatcher = request.getRequestDispatcher(PageStore.ERROR_PAGE.getPageName());
            dispatcher.forward(request, response);
        }
    }
}

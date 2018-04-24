package by.kurlovich.musicshop.servlet;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.command.CommandFactory;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.store.PageStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/mainServlet"})
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
            Command command = commandFactory.getCommand(request);
            CommandResult commandResult = command.execute(request);

            RequestDispatcher dispatcher;
            String responseType = commandResult.getResponseType().toString();

            switch (responseType) {
                case "FORWARD":
                    dispatcher = request.getRequestDispatcher(commandResult.getPage());
                    dispatcher.forward(request, response);
                    break;
                case "REDIRECT":
                    response.sendRedirect(commandResult.getPage());
                    break;
                default:
                    request.setAttribute("message", "Unknown response type.");
                    dispatcher = request.getRequestDispatcher(PageStore.ERROR_PAGE.getPageName());
                    dispatcher.forward(request, response);
            }

        } catch (CommandException e) {
            request.setAttribute("message", "exception: " + e.getCause());
            RequestDispatcher dispatcher = request.getRequestDispatcher(PageStore.ERROR_PAGE.getPageName());
            dispatcher.forward(request, response);
        }
    }
}

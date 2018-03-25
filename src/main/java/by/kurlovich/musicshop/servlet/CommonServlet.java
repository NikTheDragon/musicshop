package by.kurlovich.musicshop.servlet;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandFactory;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

        String url = (String) request.getSession(true).getAttribute("url");

        System.out.println("url= "+url);

        LOGGER.debug("Command = {}", request.getParameter("command"));

        RequestContent requestContent = new RequestContent();

        LOGGER.debug("Request content done.");

        Map<String, String[]> rm = new HashMap<>();

        rm = request.getParameterMap();

        System.out.println("prm= "+ rm.get("command")[0]);
        requestContent.setRequest(request);
        requestContent.setRequestParameters("command", request.getParameter("command").toUpperCase());

        LOGGER.debug("Request content set parameter done.");

        Command command = commandFactory.getCommand(requestContent);

        LOGGER.debug("Command done.");

        CommandResult commandResult = command.execute(requestContent);

        LOGGER.debug("Response type Command = {}", commandResult.getResponseType());

        RequestDispatcher dispatcher = request.getRequestDispatcher(commandResult.getPage());
        request.setAttribute("content", requestContent.getRequestMap());
        dispatcher.forward(request, response);
    }
}

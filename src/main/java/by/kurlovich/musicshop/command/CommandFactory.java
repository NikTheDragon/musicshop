package by.kurlovich.musicshop.command;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private static final CommandFactory instance = new CommandFactory();

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {
        String name = request.getParameter("command").toUpperCase();

        try {
            CommandType type = CommandType.valueOf(name);
            return type.getCommand();
        } catch (IllegalArgumentException ex) {
            request.setAttribute("message", "nocmd");
            request.setAttribute("cmd", name);
            CommandType type = CommandType.valueOf("SHOW_ERROR_PAGE");
            return type.getCommand();
        }
    }
}

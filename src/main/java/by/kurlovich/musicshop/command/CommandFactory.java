package by.kurlovich.musicshop.command;

import javax.servlet.http.HttpServletRequest;

public class CommandFactory {
    private final static CommandFactory instance = new CommandFactory();

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(HttpServletRequest request) {
        String name = request.getParameter("command").toUpperCase();

        try {
            CommandType type = CommandType.valueOf(name);
            return type.getCommand();
        } catch (IllegalArgumentException ex) {
            request.setAttribute("nocommand", "cmd: " + name);
            CommandType type = CommandType.valueOf("COMMAND_NOT_FOUND");
            return type.getCommand();
        }
    }
}

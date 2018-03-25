package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.content.RequestContent;

public class CommandFactory {
    private final static CommandFactory instance = new CommandFactory();

    public static CommandFactory getInstance() {
        return instance;
    }

    public Command getCommand(RequestContent content) {
        try {
            String name = content.getRequestParameter("command");
            CommandType type = CommandType.valueOf(name);
            return type.getCommand();
        } catch (IllegalArgumentException ex) {
            CommandType type = CommandType.valueOf("COMMAND_NOT_FOUND");
            return type.getCommand();
        }
    }
}

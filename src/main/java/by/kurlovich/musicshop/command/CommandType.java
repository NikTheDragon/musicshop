package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.command.impl.ChangeLanguage;
import by.kurlovich.musicshop.command.impl.CommandNotFound;
import by.kurlovich.musicshop.command.impl.ShowMainPage;

public enum CommandType {
    SHOW_MAIN_PAGE(ShowMainPage.getInstance()::execute),
    COMMAND_NOT_FOUND(CommandNotFound.getInstance()::execute),
    CHANGE_LANGUAGE(ChangeLanguage.getInstance()::execute);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

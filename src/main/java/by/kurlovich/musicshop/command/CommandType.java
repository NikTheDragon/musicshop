package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.command.impl.*;

public enum CommandType {
    SHOW_MAIN_PAGE(ShowMainPage.getInstance()::execute),
    COMMAND_NOT_FOUND(CommandNotFound.getInstance()::execute),
    CHANGE_LANGUAGE(ChangeLanguage.getInstance()::execute),
    SHOW_REG_PAGE(ShowRegPage.getInstance()::execute),
    REG_NEW_USER(RegNewUser.getInstance()::execute);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

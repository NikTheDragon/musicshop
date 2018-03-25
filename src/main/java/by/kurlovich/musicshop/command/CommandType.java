package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.receiver.CommonReceiver;
import by.kurlovich.musicshop.receiver.ErrorReceiver;

import javax.naming.Context;

public enum CommandType {
    SHOW_MAIN_PAGE(CommonReceiver.getInstance()::showMainPage),
    COMMAND_NOT_FOUND(ErrorReceiver.getInstance()::showErrorPage),
    CHANGE_LANGUAGE(CommonReceiver.getInstance()::changeLanguage);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

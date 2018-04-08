package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.command.impl.*;
import by.kurlovich.musicshop.receiver.impl.UserReceiverImpl;

public enum CommandType {
    SHOW_MAIN_PAGE(new ShowMainPage()::execute),
    COMMAND_NOT_FOUND(new CommandNotFound()::execute),
    CHANGE_LANGUAGE(new ChangeLanguage()::execute),
    SHOW_REG_PAGE(new ShowRegPage()::execute),
    REG_NEW_USER(new RegNewUser(new UserReceiverImpl())::execute),
    LOGIN_USER(new LoginUser(new UserReceiverImpl())::execute),
    SHOW_USER_PAGE(new ShowUserPage()::execute),
    LOGOUT(new Logout()::execute);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

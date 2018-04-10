package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.command.impl.*;
import by.kurlovich.musicshop.receiver.impl.UserReceiverImpl;

public enum CommandType {
    COMMAND_NOT_FOUND(new CommandNotFound()::execute),
    CHANGE_LANGUAGE(new ChangeLanguage()::execute),
    REG_NEW_USER(new RegNewUser(new UserReceiverImpl())::execute),
    LOGIN_USER(new LoginUser(new UserReceiverImpl())::execute),
    LOGOUT(new Logout()::execute),
    SHOW_MAIN_PAGE(new ShowMainPage()::execute),
    SHOW_USER_PAGE(new ShowUserPage()::execute),
    SHOW_REG_PAGE(new ShowRegPage()::execute),
    SHOW_BROWSE_TRACKS_PAGE(new ShowBrowseTracksPage()::execute);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

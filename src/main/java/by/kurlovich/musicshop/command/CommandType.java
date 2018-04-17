package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.command.admin.*;
import by.kurlovich.musicshop.command.common.*;
import by.kurlovich.musicshop.receiver.impl.GenreReceiverImpl;
import by.kurlovich.musicshop.receiver.impl.TrackReceiverImpl;
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
    SHOW_EDIT_TRACKS_PAGE(new ShowEditTracksPage()::execute),
    SHOW_EDIT_GENRES_PAGE(new ShowEditGenresPage(new GenreReceiverImpl())::execute),
    CREATE_GENRE(new CreateGenre(new GenreReceiverImpl())::execute),
    CREATE_TRACK(new CreateTrack(new TrackReceiverImpl())::execute),
    DELETE_GENRE(new DeleteGenre(new GenreReceiverImpl())::execute),
    DELETE_TRACK(new DeleteTrack(new TrackReceiverImpl())::execute),
    UPDATE_GENRE(new UpdateGenre(new GenreReceiverImpl())::execute),
    UPDATE_TRACK(new UpdateTrack(new TrackReceiverImpl())::execute);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

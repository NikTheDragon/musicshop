package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.command.admin.*;
import by.kurlovich.musicshop.command.common.*;
import by.kurlovich.musicshop.receiver.impl.*;

public enum CommandType {
    SHOW_ERROR_PAGE(new ShowErrorPage()::execute),
    CHANGE_LANGUAGE(new ChangeLanguage()::execute),
    REG_NEW_USER(new RegNewUser(new UserReceiverImpl())::execute),
    LOGIN_USER(new LoginUser(new UserReceiverImpl())::execute),
    LOGOUT(new Logout()::execute),
    SHOW_MAIN_PAGE(new ShowMainPage()::execute),
    SHOW_USER_PAGE(new ShowUserPage()::execute),
    SHOW_REG_PAGE(new ShowRegPage()::execute),
    SHOW_EDIT_TRACKS_PAGE(new ShowEditTracksPage(new TrackReceiverImpl())::execute),
    SHOW_EDIT_GENRES_PAGE(new ShowEditGenresPage(new GenreReceiverImpl())::execute),
    SHOW_EDIT_AUTHORS_PAGE(new ShowEditAuthorsPage(new AuthorReceiverImpl())::execute),
    SHOW_EDIT_ALBUMS_PAGE(new ShowEditAlbumsPage(new AlbumReceiverImpl())::execute),
    SHOW_EDIT_MIXES_PAGE(new ShowEditMixesPage(new MixReceiverImpl())::execute),
    SHOW_EDIT_MIXES_CONTENT_PAGE(new ShowEditMixesContentPage(new MixContentReceiverImpl())::execute),
    SHOW_EDIT_ALBUMS_CONTENT_PAGE(new ShowEditAlbumsContentPageCommand(new AlbumContentReceiverImpl())::execute),
    SHOW_USERS_PAGE(new ShowUsersPageCommand(new UserReceiverImpl())),
    CREATE_GENRE(new CreateGenreCommand(new GenreReceiverImpl())::execute),
    CREATE_TRACK(new CreateTrackCommand(new TrackReceiverImpl())::execute),
    CREATE_AUTHOR(new CreateAuthorCommand(new AuthorReceiverImpl())::execute),
    CREATE_ALBUM(new CreateAlbumCommand(new AlbumReceiverImpl())::execute),
    CREATE_MIX(new CreateMixCommand(new MixReceiverImpl())::execute),
    DELETE_GENRE(new DeleteGenre(new GenreReceiverImpl())::execute),
    DELETE_TRACK(new DeleteTrack(new TrackReceiverImpl())::execute),
    DELETE_AUTHOR(new DeleteAuthor(new AuthorReceiverImpl())::execute),
    DELETE_ALBUM(new DeleteAlbum(new AlbumReceiverImpl())::execute),
    DELETE_MIX(new DeleteMix(new MixReceiverImpl())::execute),
    DELETE_TRACK_FROM_MIX(new DeleteTrackFromMixCommand(new MixContentReceiverImpl())::execute),
    DELETE_TRACK_FROM_ALBUM(new DeleteTrackFromAlbumCommand(new AlbumContentReceiverImpl())::execute),
    UPDATE_GENRE(new UpdateGenre(new GenreReceiverImpl())::execute),
    UPDATE_TRACK(new UpdateTrack(new TrackReceiverImpl())::execute),
    UPDATE_AUTHOR(new UpdateAuthor(new AuthorReceiverImpl())::execute),
    UPDATE_ALBUM(new UpdateAlbum(new AlbumReceiverImpl())::execute),
    UPDATE_MIX(new UpdateMix(new MixReceiverImpl())::execute),
    BACK_TO_PAGE(new BackToPage()::execute),
    SUBMIT_TRACK(new SubmitTrackCommand()::execute),
    ADD_TRACK_TO_MIX(new AddTrackToMixCommand(new MixContentReceiverImpl())::execute),
    ADD_TRACK_TO_ALBUM(new AddTrackToAlbumCommand(new AlbumContentReceiverImpl())::execute);

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

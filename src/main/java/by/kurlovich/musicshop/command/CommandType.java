package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.command.admin.*;
import by.kurlovich.musicshop.command.common.*;
import by.kurlovich.musicshop.command.user.*;
import by.kurlovich.musicshop.receiver.impl.*;

public enum CommandType {
    SEARCH_TRACKS(new SearchTracksCommand(new TrackReceiverImpl())),
    SHOW_ERROR_PAGE(new ShowErrorPageCommand()),
    SHOW_SELECTED_ROWS(new ShowSelectedRowsCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    REG_NEW_USER(new RegNewUserCommand(new UserReceiverImpl())),
    LOGIN_USER(new LoginUserCommand(new UserReceiverImpl())),
    LOGOUT(new LogoutCommand()),
    SHOW_MAIN_PAGE(new ShowMainPageCommand()),
    SHOW_USER_PAGE(new ShowUserPageCommand()),
    SHOW_REG_PAGE(new ShowRegPageCommand()),
    SHOW_MY_TRACKS(new ShowMyTracksCommand(new UserReceiverImpl())),
    SHOW_MY_ALBUMS(new ShowMyAlbumsCommand(new UserReceiverImpl())),
    SHOW_MY_MIXES(new ShowMyMixesCommand(new UserReceiverImpl())),
    SHOW_ALL_TRACKS(new ShowAllTracksCommand(new UserReceiverImpl())),
    SHOW_ALL_ALBUMS(new ShowAllAlbumsCommand(new UserReceiverImpl())),
    SHOW_ALL_MIXES(new ShowAllMixesCommand(new UserReceiverImpl())),
    SHOW_EDIT_TRACKS_PAGE(new ShowEditTracksPageCommand(new TrackReceiverImpl(), new GenreReceiverImpl(), new AuthorReceiverImpl())),
    SHOW_EDIT_GENRES_PAGE(new ShowEditGenresPageCommand(new GenreReceiverImpl())),
    SHOW_EDIT_AUTHORS_PAGE(new ShowEditAuthorsPageCommand(new AuthorReceiverImpl(), new GenreReceiverImpl())),
    SHOW_EDIT_ALBUMS_PAGE(new ShowEditAlbumsPageCommand(new AlbumReceiverImpl(), new GenreReceiverImpl(), new AuthorReceiverImpl())),
    SHOW_EDIT_MIXES_PAGE(new ShowEditMixesPageCommand(new MixReceiverImpl(), new GenreReceiverImpl())),
    SHOW_EDIT_MIXES_CONTENT_PAGE(new ShowEditMixesContentPageCommand(new MixContentReceiverImpl(), new TrackReceiverImpl(), new MixReceiverImpl())),
    SHOW_EDIT_ALBUMS_CONTENT_PAGE(new ShowEditAlbumsContentPageCommand(new AlbumContentReceiverImpl(), new TrackReceiverImpl(), new AlbumReceiverImpl())),
    SHOW_USERS_PAGE(new ShowUsersPageCommand(new UserReceiverImpl())),
    SHOW_EDIT_USER_PAGE(new ShowEditUserPageCommand(new UserReceiverImpl())),
    SHOW_PERSONAL_PAGE(new ShowPersonalPageCommand()),
    SHOW_POINT_MANAGEMENT_PAGE(new ShowPointManagementPageCommand()),
    SHOW_MIX_CONTENT(new ShowMixContentCommand(new UserReceiverImpl(), new MixReceiverImpl())),
    SHOW_ALBUM_CONTENT(new ShowAlbumContentCommand(new UserReceiverImpl(), new AlbumReceiverImpl())),
    CREATE_GENRE(new CreateGenreCommand(new GenreReceiverImpl())),
    CREATE_TRACK(new CreateTrackCommand(new TrackReceiverImpl())),
    CREATE_AUTHOR(new CreateAuthorCommand(new AuthorReceiverImpl())),
    CREATE_ALBUM(new CreateAlbumCommand(new AlbumReceiverImpl())),
    CREATE_MIX(new CreateMixCommand(new MixReceiverImpl())),
    DELETE_GENRE(new DeleteGenreCommand(new GenreReceiverImpl())),
    DELETE_TRACK(new DeleteTrackCommand(new TrackReceiverImpl())),
    DELETE_AUTHOR(new DeleteAuthorCommand(new AuthorReceiverImpl())),
    DELETE_ALBUM(new DeleteAlbumCommand(new AlbumReceiverImpl())),
    DELETE_MIX(new DeleteMixCommand(new MixReceiverImpl())),
    DELETE_TRACK_FROM_MIX(new DeleteTrackFromMixCommand(new MixContentReceiverImpl())),
    DELETE_TRACK_FROM_ALBUM(new DeleteTrackFromAlbumCommand(new AlbumContentReceiverImpl())),
    UPDATE_GENRE(new UpdateGenreCommand(new GenreReceiverImpl())),
    UPDATE_TRACK(new UpdateTrackCommand(new TrackReceiverImpl())),
    UPDATE_AUTHOR(new UpdateAuthorCommand(new AuthorReceiverImpl())),
    UPDATE_ALBUM(new UpdateAlbumCommand(new AlbumReceiverImpl())),
    UPDATE_MIX(new UpdateMixCommand(new MixReceiverImpl())),
    UPDATE_USER(new UpdateUserCommand(new UserReceiverImpl())),
    UPDATE_PERSONAL_INFO(new UpdatePersonalInfoCommand(new UserReceiverImpl())),
    BACK_TO_PAGE(new BackToPageCommand()),
    FORM_MIX_CONTENT_INPUT_DATA(new FormMixContentInputDataCommand(new TrackReceiverImpl())),
    ADD_TRACK_TO_MIX(new AddTrackToMixCommand(new MixContentReceiverImpl())),
    ADD_TRACK_TO_ALBUM(new AddTrackToAlbumCommand(new AlbumContentReceiverImpl())),
    ADD_POINTS(new AddPointsCommand(new UserReceiverImpl())),
    BUY_TRACK(new BuyTrackCommand(new UserReceiverImpl())),
    BUY_ALBUM(new BuyAlbumCommand(new UserReceiverImpl())),
    BUY_MIX(new BuyMixCommand(new UserReceiverImpl()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}

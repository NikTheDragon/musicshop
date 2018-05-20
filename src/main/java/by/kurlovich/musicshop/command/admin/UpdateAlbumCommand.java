package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.command.base.AlbumCommand;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

public class UpdateAlbumCommand extends AlbumCommand {
    private EntityReceiver receiver;

    public UpdateAlbumCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Album album) throws CommandException {
        try {
            return receiver.updateEntity(album);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateAlbumCommand.\n" + e, e);
        }
    }
}

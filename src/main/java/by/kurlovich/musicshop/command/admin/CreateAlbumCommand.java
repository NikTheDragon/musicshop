package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

public class CreateAlbumCommand extends AbstractAlbumCommand {
    private EntityReceiver receiver;

    public CreateAlbumCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Album album) throws CommandException {
        try {
            return receiver.addNewEntity(album);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateAlbumCommand.\n" + e, e);
        }
    }
}

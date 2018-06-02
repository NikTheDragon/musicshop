package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Album;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAlbumCommand extends AbstractAlbumCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAlbumCommand.class);
    private EntityReceiver receiver;

    public UpdateAlbumCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Album album) throws CommandException {
        try {
            LOGGER.info("update album executed.");

            return receiver.updateEntity(album);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateAlbumCommand.\n" + e, e);
        }
    }
}

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTrackCommand extends AbstractTrackCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateTrackCommand.class);
    private EntityReceiver receiver;

    public UpdateTrackCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Track track) throws CommandException {
        try {
            LOGGER.info("update track executed.");

            return receiver.updateEntity(track);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateTrackCommand.\n" + e, e);
        }
    }
}

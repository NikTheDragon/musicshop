package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTrackCommand extends AbstractTrackCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateTrackCommand.class);
    private EntityReceiver receiver;

    public CreateTrackCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Track track) throws CommandException {
        try {
            LOGGER.info("create track executed.");

            return receiver.addNewEntity(track);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateTrackCommand.\n" + e, e);
        }
    }
}

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

public class DeleteTrackCommand extends AbstractTrackCommand {
    private EntityReceiver receiver;

    public DeleteTrackCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Track track) throws CommandException {
        try {
            return receiver.deleteEntity(track);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteTrackCommand.\n" + e, e);
        }
    }
}

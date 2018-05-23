package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.command.base.TrackCommand;
import by.kurlovich.musicshop.entity.Track;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

public class UpdateTrackCommand extends TrackCommand {
    private EntityReceiver receiver;

    public UpdateTrackCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Track track) throws CommandException {
        try {
            return receiver.updateEntity(track);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateTrackCommand.\n" + e, e);
        }
    }
}

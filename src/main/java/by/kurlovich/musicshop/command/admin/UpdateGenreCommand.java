package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

public class UpdateGenreCommand extends AbstractGenreCommand {
    private EntityReceiver receiver;

    public UpdateGenreCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Genre genre) throws CommandException {
        try {
            return receiver.updateEntity(genre);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateGenreCommand.\n" + e, e);
        }
    }
}

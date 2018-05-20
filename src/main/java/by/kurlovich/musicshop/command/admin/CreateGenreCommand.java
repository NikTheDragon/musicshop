package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.command.base.GenreCommand;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

public class CreateGenreCommand extends GenreCommand {
    private EntityReceiver receiver;

    public CreateGenreCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Genre genre) throws CommandException {
        try {
            return receiver.addNewEntity(genre);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateGenreCommand.\n" + e, e);
        }
    }
}

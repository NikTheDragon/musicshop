package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.command.base.GenreCommand;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

public class DeleteGenreCommand extends GenreCommand {
    private EntityReceiver receiver;

    public DeleteGenreCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Genre genre) throws CommandException {
        try {
            return receiver.deleteEntity(genre);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteGenreCommand.\n" + e, e);
        }
    }
}

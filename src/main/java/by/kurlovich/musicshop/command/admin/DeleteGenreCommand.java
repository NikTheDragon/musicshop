package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteGenreCommand extends AbstractGenreCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteGenreCommand.class);
    private EntityReceiver receiver;

    public DeleteGenreCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Genre genre) throws CommandException {
        try {
            LOGGER.info("delete genre executed.");

            return receiver.deleteEntity(genre);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteGenreCommand.\n" + e, e);
        }
    }
}

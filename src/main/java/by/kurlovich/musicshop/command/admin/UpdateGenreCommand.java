package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateGenreCommand extends AbstractGenreCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateGenreCommand.class);
    private EntityReceiver receiver;

    public UpdateGenreCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Genre genre) throws CommandException {
        try {
            LOGGER.info("update genre executed.");

            return receiver.updateEntity(genre);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateGenreCommand.\n" + e, e);
        }
    }
}

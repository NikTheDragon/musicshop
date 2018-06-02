package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Genre;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateGenreCommand extends AbstractGenreCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateGenreCommand.class);
    private EntityReceiver receiver;

    public CreateGenreCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Genre genre) throws CommandException {
        try {
            LOGGER.info("create genre executed.");

            return receiver.addNewEntity(genre);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateGenreCommand.\n" + e, e);
        }
    }
}

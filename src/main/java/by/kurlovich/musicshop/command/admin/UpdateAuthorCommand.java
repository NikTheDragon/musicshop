package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAuthorCommand extends AbstractAuthorCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateAuthorCommand.class);
    private EntityReceiver receiver;

    public UpdateAuthorCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Author author) throws CommandException {
        try {
            LOGGER.info("update author executed.");

            return receiver.updateEntity(author);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateAuthorCommand.\n" + e, e);
        }
    }
}

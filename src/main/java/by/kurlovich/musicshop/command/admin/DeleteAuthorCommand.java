package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteAuthorCommand extends AbstractAuthorCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteAuthorCommand.class);
    private EntityReceiver receiver;

    public DeleteAuthorCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Author author) throws CommandException {
        try {
            LOGGER.info("delete author executed.");

            return receiver.deleteEntity(author);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteAuthorCommand.\n" + e, e);
        }
    }
}

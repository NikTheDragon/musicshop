package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Author;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAuthorCommand extends AbstractAuthorCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAuthorCommand.class);
    private EntityReceiver receiver;

    public CreateAuthorCommand (EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Author author) throws CommandException {
        try {
            LOGGER.info("create author executed.");

            return receiver.addNewEntity(author);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateAuthorCommand.\n" + e, e);
        }
    }
}

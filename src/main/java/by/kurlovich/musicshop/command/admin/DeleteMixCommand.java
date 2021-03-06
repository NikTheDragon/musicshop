package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteMixCommand extends AbstractMixCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteMixCommand.class);
    private EntityReceiver receiver;

    public DeleteMixCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Mix mix) throws CommandException {
        try {
            LOGGER.info("delete mix executed.");

            return receiver.deleteEntity(mix);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in DeleteMixCommand.\n" + e, e);
        }
    }
}

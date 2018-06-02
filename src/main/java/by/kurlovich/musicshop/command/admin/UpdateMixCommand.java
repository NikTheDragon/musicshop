package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMixCommand extends AbstractMixCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateMixCommand.class);
    private EntityReceiver receiver;

    public UpdateMixCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Mix mix) throws CommandException {
        try {
            LOGGER.info("update mix executed.");

            return receiver.updateEntity(mix);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in UpdateMixCommand.\n" + e, e);
        }
    }
}

package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateMixCommand extends AbstractMixCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMixCommand.class);
    private EntityReceiver receiver;

    public CreateMixCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Mix mix) throws CommandException {
        try {
            LOGGER.info("create mix executed.");

            return receiver.addNewEntity(mix);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateMixCommand.\n" + e, e);
        }
    }
}

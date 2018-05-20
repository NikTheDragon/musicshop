package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.command.base.MixCommand;
import by.kurlovich.musicshop.entity.Mix;
import by.kurlovich.musicshop.receiver.EntityReceiver;
import by.kurlovich.musicshop.receiver.ReceiverException;

public class CreateMixCommand extends MixCommand {
    private EntityReceiver receiver;

    public CreateMixCommand(EntityReceiver receiver) {
        super(receiver);
        this.receiver = receiver;
    }

    @Override
    public boolean doCommand(Mix mix) throws CommandException {
        try {
            return receiver.addNewEntity(mix);
        } catch (ReceiverException e) {
            throw new CommandException("Exception in CreateMixCommand.\n" + e, e);
        }
    }
}

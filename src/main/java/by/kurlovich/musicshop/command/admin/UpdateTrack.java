package by.kurlovich.musicshop.command.admin;

import by.kurlovich.musicshop.command.Command;
import by.kurlovich.musicshop.command.CommandException;
import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.receiver.TrackReceiver;

import javax.servlet.http.HttpServletRequest;

public class UpdateTrack implements Command {

    private TrackReceiver receiver;

    public UpdateTrack(TrackReceiver receiver) {

        this.receiver = receiver;
    }

    @Override
    public CommandResult execute(HttpServletRequest request) throws CommandException {
        return null;
    }
}

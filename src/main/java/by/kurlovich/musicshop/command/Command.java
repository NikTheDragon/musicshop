package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.content.CommandResult;
import by.kurlovich.musicshop.content.RequestContent;

@FunctionalInterface
public interface Command {
    CommandResult execute(RequestContent requestContent);
}

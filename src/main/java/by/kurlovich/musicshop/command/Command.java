package by.kurlovich.musicshop.command;

import by.kurlovich.musicshop.web.CommandResult;

import javax.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    CommandResult execute(HttpServletRequest request) throws CommandException;
}

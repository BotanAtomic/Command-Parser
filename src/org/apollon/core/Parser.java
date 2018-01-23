package org.apollon.core;

import org.apollon.api.CommandHandler;
import org.apollon.command.Command;
import org.apollon.option.Option;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Parser {

    private final Set<Command> commands = new HashSet<>();
    private CommandHandler handler = CommandHandler.empty();
    private boolean ignoreCase = false;

    public Parser addCommand(Command command) {
        this.commands.add(command);
        return this;
    }

    public void analyse(String input) {
        String[] data = input.split(" ");

        String commandName = data[0];

        Command command = commands.stream().filter(c -> (ignoreCase ? c.getName().equalsIgnoreCase(commandName)
                : c.getName().equals(commandName))).findAny().orElse(null);


        if (command == null) {
            handler.commandNotFound(commandName);
        } else {
            List<String> options = getOptions(data);

            List<String> invalidOptions = options.stream().filter(s -> command.getOptions().stream()
                    .noneMatch(o -> "-".concat(o.getName()).equals(s))).collect(Collectors.toList());

            if (invalidOptions.size() > 0) {
                handler.notRecognizedOptions(invalidOptions, command);
                return;
            }

            List<Option> validOptions = command.getOptions().stream().filter(o ->
                    options.contains("-".concat(o.getName()))).collect(Collectors.toList());

            if (validOptions.size() > command.getMaxArguments()) {
                return;
            }

            command.resultAction().event(validOptions, getValues(data));
        }

    }

    private List<String> getValues(final String[] data) {
        return IntStream.range(0, data.length).filter(i -> data[i].charAt(0) != '-' && i > 0).mapToObj(i -> data[i]).collect(Collectors.toList());
    }

    private List<String> getOptions(String[] data) {
        return Stream.of(data).filter(d -> d.charAt(0) == '-').collect(Collectors.toList());
    }


    public void setHandler(CommandHandler handler) {
        this.handler = handler;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}

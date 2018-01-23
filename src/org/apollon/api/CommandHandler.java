package org.apollon.api;

import org.apollon.command.Command;

import java.util.List;

public interface CommandHandler {

    static CommandHandler empty() {
        return new CommandHandler() {
            @Override
            public boolean canExecute(Command command) {
                return true;
            }

            @Override
            public void commandNotFound(String input) {

            }

            @Override
            public void notRecognizedOptions(List<String> options, Command command) {

            }

        };
    }

    boolean canExecute(Command command);

    void commandNotFound(String input);

    void notRecognizedOptions(List<String> options, Command command);

}

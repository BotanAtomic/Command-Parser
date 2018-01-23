package org.apollon.command;

import org.apollon.api.ResultAction;
import org.apollon.option.Option;

import java.util.HashSet;
import java.util.Set;

public class Command {

    private String name, description;

    private Set<Option> options = new HashSet<>();

    private ResultAction resultAction;

    private int maxArguments = Integer.MAX_VALUE;

    private Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Command create(String name) {
        return new Command(name, "");
    }

    public static Command create(String name, String description) {
        return new Command(name, description);
    }

    public Command addOption(Option option) {
        this.options.add(option);
        return this;
    }

    public Command onResult(ResultAction resultAction) {
        this.resultAction = resultAction;
        return this;
    }

    public String getName() {
        return name;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public ResultAction resultAction() {
        return resultAction;
    }

    public int getMaxArguments() {
        return maxArguments;
    }

    public Command setMaxArguments(int maxArguments) {
        this.maxArguments = maxArguments;
        return this;
    }


}

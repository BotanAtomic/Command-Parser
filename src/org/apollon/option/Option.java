package org.apollon.option;

public class Option {
    private String name, description;

    private Object value;

    private Option(String name, String description, Object value) {
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public static Option create(String name) {
        return new Option(name, "", null);
    }

    public static Option create(String name, Object value) {
        return new Option(name, "", value);
    }

    public static Option create(String name, String description) {
        return new Option(name, description, null);
    }

    public static Option create(String name, String description, Object value) {
        return new Option(name, description, value);
    }

    public String getName() {
        return name;
    }

    public <T> T value(Class<T> clazz) {
        return clazz.cast(value);
    }
}

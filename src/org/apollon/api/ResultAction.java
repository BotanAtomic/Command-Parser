package org.apollon.api;

import org.apollon.option.Option;

import java.util.List;
import java.util.Set;

public interface ResultAction {

    void event(List<Option> declaredOptions, List<String> values);

}

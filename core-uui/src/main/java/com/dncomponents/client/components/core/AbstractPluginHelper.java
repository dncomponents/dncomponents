package com.dncomponents.client.components.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPluginHelper implements PluginHelper {

    protected Map<String, List<String>> arguments = new HashMap<>();
    protected Map<String, List<String>> tags = new HashMap<>();

    @Override
    public Map<String, List<String>> getArguments() {
        return arguments;
    }

    @Override
    public Map<String, List<String>> getTags() {
        return tags;
    }
}

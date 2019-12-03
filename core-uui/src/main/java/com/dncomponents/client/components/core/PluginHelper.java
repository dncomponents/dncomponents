package com.dncomponents.client.components.core;

import java.util.List;
import java.util.Map;

public interface PluginHelper {
    String getId();

    Class getClazz();

    Map<String, List<String>> getArguments();

    Map<String, List<String>> getTags();
}

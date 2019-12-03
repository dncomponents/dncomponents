package com.dncomponents.client.views.core;

import com.dncomponents.client.views.core.pcg.ComponentUi;

import java.util.Map;

public interface UiFactory<T extends ComponentUi> {
    T getUi(String ui, Map<String, String> attributes);
}

package com.dncomponents.client.components.core.events.focus;

public interface Focusable {

    int getTabIndex();

    void setAccessKey(char key);

    void setFocus(boolean focused);

    void setTabIndex(int index);
}
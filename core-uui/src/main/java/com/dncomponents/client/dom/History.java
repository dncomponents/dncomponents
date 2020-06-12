package com.dncomponents.client.dom;

import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.PopstateHandler;
import elemental2.dom.DomGlobal;
import elemental2.dom.PopStateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author nikolasavic
 */
public class History {

    private static List<Consumer<String>> handlerList = new ArrayList<>();

    public static HandlerRegistration addValueChangeHandler(Consumer<String> handler) {
        handlerList.add(handler);
        return new HandlerRegistration() {
            @Override
            public void removeHandler() {
                handlerList.remove(handler);
            }
        };
    }

    public static void removeValueChangeHandler(Consumer<String> handler) {
        handlerList.remove(handler);
    }

    public static void clearHandlers() {
        handlerList.clear();
    }


    public static void newItem(String historyToken, boolean issueEvent) {
        if (issueEvent)
            DomGlobal.location.hash=(historyToken);
        else
            DomGlobal.window.history.replaceState(null, null, "#" + historyToken);
    }

    public static void update(String token) {
        handlerList.forEach(e -> e.accept(token.substring(1)));
    }

    static {
        DomGlobal.window.addEventListener(PopstateHandler.TYPE, new PopstateHandler() {
            @Override
            public void onPopStateChanged(PopStateEvent event) {
                final String hash = DomGlobal.location.hash;
                update(hash);
            }
        });
    }

    public static void fireCurrentHistoryState() {
        final String hash = DomGlobal.location.hash;
        newItem("temp", false);
        newItem(hash.substring(1), true);
    }

}
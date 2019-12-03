package com.dncomponents.client.components.core;

import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.core.events.focus.HasFocusHandlers;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.dom.handlers.OnFocusHandler;
import com.dncomponents.client.views.FocusComponentView;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasEnabled;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.FocusEvent;
import elemental2.dom.HTMLElement;

/**
 * Abstract base class for most components that can receive keyboard focus.
 */
public class BaseFocusComponent<T, V extends FocusComponentView> extends BaseComponent<T, V>
        implements Focusable, HasFocusHandlers, HasBlurHandlers, HasEnabled {

    boolean enabled = true;

    public BaseFocusComponent(V view) {
        super(view);
        initComplexFocus();
    }

    @Override
    public int getTabIndex() {
        return focusElement().tabIndex;
    }

    @Override
    public void setAccessKey(char key) {
        focusElement().setAttribute("accesskey", key);
    }

    @Override
    public void setFocus(boolean focused) {
        if (focused)
            focusElement().focus();
        else
            asElement().blur();
    }

    @Override
    public void setTabIndex(int index) {
        focusElement().setAttribute("tabindex", index);
    }

    protected HTMLElement focusElement() {
        return view.getFocusElement();
    }

    @Override
    public HandlerRegistration addFocusHandler(OnFocusHandler handler) {
        return handler.addTo(asElement());
    }

    @Override
    public HandlerRegistration addBlurHandler(OnBlurHandler handler) {
        return handler.addTo(asElement());
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        view.setEnabled(enabled);
    }

    protected boolean focused;
    private boolean firstTimeFocused;

    private void initComplexFocus() {
        asElement().addEventListener("focusout", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                focused = false;
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        if (!focused) {
                            asElement().dispatchEvent(new FocusEvent("blur"));
                            firstTimeFocused = false;
                        }
                    }
                };
                timer.schedule(200);
            }
        });
        asElement().addEventListener("focusin", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                if (!firstTimeFocused) {
                    asElement().dispatchEvent(new FocusEvent("focus"));
                }
                focused = true;
                firstTimeFocused = true;
            }
        });
    }
}

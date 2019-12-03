package com.dncomponents.client.components.button;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.events.click.HasClickHandlers;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import com.dncomponents.client.views.core.ui.button.ButtonViewSlots;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.Element;

import java.util.Collections;
import java.util.Map;

/**
 * @author nikolasavic
 */
public class Button<T> extends BaseComponent<T, ButtonView> implements HasClickHandlers {

    private boolean enabled;

    public Button() {
        this(Ui.get().getButtonView());
    }

    public Button(String text) {
        this();
        setText(text);
        setRenderer(new ButtonRenderer<T>() {
            @Override
            public void render(T t, ButtonViewSlots slots) {
                slots.getMainSlot().innerHTML = t + "";
            }
        });
    }

    public Button(String text, ClickHandler clickHandler) {
        this();
        setText(text);
        addClickHandler(clickHandler);
    }

    public Button(ButtonView view, String text) {
        super(view);
        setText(text);
    }

    public Button(ButtonView view) {
        super(view);
    }


    public void setText(String text) {
        view.setText(text);
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return handler.addTo(asElement());
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        view.setEnabled(this.enabled);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public interface ButtonRenderer<T> extends Renderer<T, ButtonViewSlots> {
    }

    public void setRenderer(ButtonRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    protected ButtonViewSlots getViewSlots() {
        return (ButtonViewSlots) super.getViewSlots();
    }

    public static class ButtonHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        public static final String DISABLED = "disabled";

        private static ButtonHtmlParser instance;

        private ButtonHtmlParser() {
            arguments.put(DISABLED, Collections.emptyList());
        }

        public static ButtonHtmlParser getInstance() {
            if (instance == null)
                return instance = new ButtonHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            Button button;
            ButtonView view = getView(Button.class, htmlElement, elements);
            if (view != null)
                button = new Button(view);
            else
                button = new Button();

            button.setEnabled(!htmlElement.hasAttribute(DISABLED));
            button.getViewSlots().getMainSlot().innerHTML = htmlElement.innerHTML;
            replaceAndCopy(htmlElement, button);
            return button;
        }

        @Override
        public String getId() {
            return "dn-button";
        }

        @Override
        public Class getClazz() {
            return Button.class;
        }
    }

}
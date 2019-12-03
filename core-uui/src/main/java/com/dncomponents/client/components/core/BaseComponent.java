package com.dncomponents.client.components.core;

import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.components.core.events.HasHandlers;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.ViewSlots;
import com.dncomponents.client.views.core.pcg.View;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.CustomEvent;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;


/**
 * Base class for all components.
 * View logic is separated in {@link View}.
 * View implementations can be injected through constructor for concrete object
 * and through static methods {@link Ui} on application level.
 *
 * @author nikolasavic
 */
public abstract class BaseComponent<T, V extends View> implements IsElement, HasUserValue<T>,
        HasHandlers, com.google.gwt.event.shared.HasHandlers {

    protected final V view;
    protected T userObject;
    protected Renderer renderer;

    protected ViewSlots viewSlots;

    protected ViewSlots getViewSlots() {
        if (view instanceof HasViewSlots)
            viewSlots = ((HasViewSlots) view).getViewSlots();
        return viewSlots;
    }

    public static WeakMap allComponents = new WeakMap();


    public static <C> C getSource(Event event) {
        return (C) allComponents.get(event.target);
    }

    //
    public static <T extends BaseComponent> T source(Event event) {
        return (T) allComponents.get(event.target);
    }

    public BaseComponent(V view) {
        if (view == null)
            throw new NullPointerException("View can't be null! Please check view initialisation.");
        this.view = view;
        HTMLElement viewElement = this.view.asElement();
        if (viewElement == null)
            throw new IllegalStateException("View " + view.getClass() + " root element is null. Please check if you properly bind elements!");
        allComponents.set(viewElement, this);
        if (Ui.isDebug()) {
            viewElement.setAttribute("component-class", this.getClass().getSimpleName());
            viewElement.setAttribute("view-class", this.view.getClass().getSimpleName());
        }
    }

    protected V getView() {
        return view;
    }

    @Override
    public T getUserObject() {
        return userObject;
    }

    //todo rename to setUserValue ?
    @Override
    public void setUserObject(T userObject) {
        this.userObject = userObject;
        render();
    }

    protected void render() {
        if (renderer != null) {
            renderer.render(userObject, getViewSlots());
        }
    }


    //todo remove... overrider getViewSlots() instead
    public interface DrawOnSlots {
        void apply(ViewSlots slots);
    }

    public void drawOnSlotsBase(DrawOnSlots drawOnSlots) {
        drawOnSlots.apply(getViewSlots());
    }

    @Override
    public HTMLElement asElement() {
        return getView().asElement();
    }

    public interface Renderer<T, R extends ViewSlots> {
        void render(T t, R slots);
    }

    protected void setRendererBase(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        this.asElement().dispatchEvent(event);
    }

    //todo remove handler
    public HandlerRegistration addHandler(BaseEventListener handler) {
        return handler.addTo(this.asElement());
    }

    //todo remove

    private HandlerManager handlerManager;

    protected HandlerManager ensureHandlers() {
        if (handlerManager == null) {
            handlerManager = new HandlerManager(this);
        }
        return handlerManager;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        if (handlerManager != null) {
            handlerManager.fireEvent(event);
        }
    }

    //end todo remove


    public void setStyle(String style) {
        DomUtil.setStyle(asElement(), style);
    }

    public void addStyle(String style) {
        DomUtil.addStyle(asElement(), style);
    }

    public void removeStyle(String style) {
        DomUtil.removeStyle(asElement(), style);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
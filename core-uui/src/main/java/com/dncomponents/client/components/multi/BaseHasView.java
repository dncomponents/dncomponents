package com.dncomponents.client.components.multi;

import com.dncomponents.client.components.core.events.value.TakesValue;
import com.dncomponents.client.views.HasViewSlots;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.Renderer;
import com.dncomponents.client.views.ViewSlots;
import com.dncomponents.client.views.core.pcg.View;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public abstract class BaseHasView<T, V extends View> implements TakesValue<T>, IsElement {

    protected Renderer renderer;

    protected T value;

    protected final V view;

    protected ViewSlots viewSlots;


    public BaseHasView(V view) {
        this.view = view;
    }

    @Override
    public HTMLElement asElement() {
        return view.asElement();
    }

    protected V getView() {
        return view;
    }


    protected void setRendererBase(Renderer renderer) {
        this.renderer = renderer;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        render();
    }

    protected void render() {
        if (renderer != null) {
            renderer.render(value, getViewSlots());
        }
    }

    protected ViewSlots getViewSlots() {
        if (view instanceof HasViewSlots)
            viewSlots = ((HasViewSlots) view).getViewSlots();
        return viewSlots;
    }

}

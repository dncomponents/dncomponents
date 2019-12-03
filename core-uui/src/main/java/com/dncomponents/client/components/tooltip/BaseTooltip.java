package com.dncomponents.client.components.tooltip;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.popover.Popper;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.*;
import com.dncomponents.client.views.core.EnumLookUp;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseTooltip<T, C extends TooltipView> extends BaseComponent<T, C> {
    public enum Orientation {
        TOP, BOTTOM, LEFT, RIGHT;
        public static EnumLookUp<Orientation> lookUp = new EnumLookUp<>(Orientation.values());
    }

    public enum Trigger {
        HOVER, CLICK, FOCUS, HOVER_FOCUS, NONE;
        public static EnumLookUp<Trigger> lookUp = new EnumLookUp<>(Trigger.values());
    }

    private Orientation orientation = Orientation.BOTTOM;
    protected Trigger trigger = Trigger.HOVER;
    private HTMLElement owner;
    private boolean showing;
    private Popper popper;
    private boolean focused;
    private List<HandlerRegistration> handlers = new ArrayList<>();

    public BaseTooltip(C view) {
        super(view);
        addHandler((ClickHandler) Event::stopPropagation);
    }

    public void setContent(String str) {
        view.setContent(str);
    }

    public void setContent(HTMLElement element) {
        view.setContent(element);
    }

    private void addAndShow() {
        DomUtil.addToBody(this);
        Popper.Defaults def = Popper.Defaults.create();
        def.setPlacement(orientation.name().toLowerCase());
        popper = new Popper(owner, BaseTooltip.this.asElement(), def);
        popper.update();
        showing = true;
        updatePosition();
    }

    private void remove() {
        asElement().remove();
        showing = false;
    }

    protected abstract void fireShowEvent();

    protected abstract void fireCloseEvent();

    public void show() {
        if (!isShowing()) {
            addAndShow();
            fireShowEvent();
        }
    }

    public void hide() {
        if (isShowing()) {
            remove();
            fireCloseEvent();
        }
    }


    public void updatePosition() {
        calculatePosition();
    }

    public boolean isShowing() {
        return showing;
    }

    public Element getOwner() {
        return owner;
    }

    public void setOwner(HTMLElement owner) {
        this.owner = owner;
        setTrigger(Trigger.HOVER);
    }


    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        if (this.orientation == null)
            this.orientation = Orientation.BOTTOM;
        switch (this.orientation) {
            case BOTTOM:
                view.setBottomOrientation();
                break;
            case TOP:
                view.setTopOrientation();
                break;
            case LEFT:
                view.setLeftOrientation();
                break;
            case RIGHT:
                view.setRightOrientation();
                break;
        }
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
        if (this.trigger == null)
            this.trigger = Trigger.HOVER;
        handlers.forEach(HandlerRegistration::removeHandler);
        switch (this.trigger) {
            case HOVER:
                handlers.add(DomUtil.addHandler(owner, (MouseOverHandler) mouseEvent -> show()));
                handlers.add(DomUtil.addHandler(owner, (MouseOutHandler) mouseEvent -> hide()));
                break;
            case CLICK:
                handlers.add(DomUtil.addHandler(owner, (ClickHandler) mouseEvent -> {
                    mouseEvent.stopPropagation();
                    show();
                }));
                handlers.add(DomUtil.addHandler(DomGlobal.document.body, (ClickHandler) mouseEvent -> hide()));
                break;
            case FOCUS:
                handlers.add(DomUtil.addHandler(owner, (OnFocusHandler) mouseEvent -> show()));
                handlers.add(DomUtil.addHandler(owner, (OnBlurHandler) mouseEvent -> hide()));
                break;
            case HOVER_FOCUS:
                handlers.add(DomUtil.addHandler(owner, (MouseOverHandler) mouseEvent -> show()));
                handlers.add(DomUtil.addHandler(owner, (MouseOutHandler) mouseEvent -> {
                    if (!focused)
                        hide();
                }));
                handlers.add(DomUtil.addHandler(owner, (OnFocusHandler) focusEvent -> {
                    show();
                    focused = true;
                }));
                handlers.add(DomUtil.addHandler(owner, (OnBlurHandler) focusEvent -> {
                    hide();
                    focused = false;
                }));
                break;
        }
    }

    private void calculatePosition() {
        switch (orientation) {
            case BOTTOM:
                view.calculatePositionBottom(owner);
                break;
            case TOP:
                view.calculatePositionTop(owner);
                break;
            case LEFT:
                view.calculatePositionLeft(owner);
                break;
            case RIGHT:
                view.calculatePositionRight(owner);
                break;
        }
    }

}

package com.dncomponents.client.components.popover;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.HtmlParser;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.close.CloseEvent;
import com.dncomponents.client.components.core.events.close.CloseHandler;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.components.tooltip.BaseTooltip;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.Renderer;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.popover.PopoverView;
import com.dncomponents.client.views.core.ui.popover.PopoverViewSlots;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.Map;

/**
 * @author nikolasavic
 */
public class Popover<T> extends BaseTooltip<T, PopoverView> implements HasCloseHandlers<Popover<T>>, HasOpenHandlers<Popover<T>> {

    {
        trigger = Trigger.CLICK;
    }

    public Popover() {
        super(Ui.get().getPopoverView());
    }

    public Popover(HTMLElement owner, Orientation orientation) {
        this(orientation);
        setOwner(owner);
    }

    public Popover(HTMLElement owner, Orientation orientation, String title, String content) {
        this(orientation);
        setOwner(owner);
        setTitle(title);
        setContent(content);
    }

    public Popover(HTMLElement owner, Orientation orientation, Trigger trigger) {
        this(orientation);
        setOwner(owner);
        setTrigger(trigger);
    }

    public Popover(HTMLElement owner, Orientation orientation, Trigger trigger, String content) {
        this(orientation);
        setOwner(owner);
        setTrigger(trigger);
        setContent(content);
    }

    public Popover(BaseTooltip.Orientation orientation) {
        this();
        setOrientation(orientation);
    }

    public void setTitle(String title) {
        view.setPopoverTitle(title);
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler<Popover<T>> handler) {
        return handler.addTo(asElement());
    }

    @Override
    public HandlerRegistration addOpenHandler(OpenHandler<Popover<T>> handler) {
        return handler.addTo(asElement());
    }

    @Override
    protected void fireShowEvent() {
        OpenEvent.fire(this, this);
    }

    @Override
    protected void fireCloseEvent() {
        CloseEvent.fire(this, this);
    }

    public interface PopoverRenderer<T> extends Renderer<T, PopoverViewSlots> {
    }

    public void setRenderer(PopoverRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

    public static class PopoverHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static PopoverHtmlParser instance;
        public static final String ORIENTATION = "orientation";
        public static final String TRIGGER = "trigger";
        public static final String OWNER = "owner";

        private PopoverHtmlParser() {
            arguments.put(OWNER, Collections.emptyList());
            arguments.put(ORIENTATION, Orientation.lookUp.toStringList());
            arguments.put(TRIGGER, Trigger.lookUp.toStringList());
        }

        public static PopoverHtmlParser getInstance() {
            if (instance == null)
                return instance = new PopoverHtmlParser();
            return instance;
        }

        @Override
        public Popover parse(Element html, Map<String, ?> templateElement) {
            if (!html.hasAttribute("ui-field"))
                throw new RuntimeException("popover should have ui-field argument");

            Orientation orientation = Orientation.BOTTOM;
            if (html.hasAttribute(ORIENTATION)) {
                String or = html.getAttribute(ORIENTATION);
                Orientation orValue = Orientation.lookUp.getValue(or);
                if (orValue != null)
                    orientation = orValue;
            }
            Popover popover = new Popover(orientation);
            NodeList<Element> titles = html.getElementsByTagName("title");
            for (int i = 0; i < titles.length; i++) {
                popover.getViewSlots().getTitle().innerHTML = titles.getAt(i).textContent;
                break;
            }
            NodeList<Element> contents = html.getElementsByTagName("content");
            for (int i = 0; i < contents.length; i++) {
                popover.getViewSlots().getContent().innerHTML = contents.getAt(i).innerHTML;
                break;
            }
            DomUtil.copyAllAttributes(html, popover.asElement());
            HTMLElement toReplace = DomUtil.createDiv("<dn-popover-after></dn-popover-after>");
            DomUtil.copyAllAttributes(html, toReplace);
            toReplace.setAttribute("pp", toReplace.getAttribute("ui-field"));
            toReplace.removeAttribute("ui-field");
            html.parentNode.appendChild(toReplace);
            replaceAndCopy(html, popover);
            return popover;
        }

        @Override
        public String getId() {
            return "dn-popover";
        }

        @Override
        public Class getClazz() {
            return Popover.class;
        }

    }

    public static class PopoverAfterHtmlParser extends AbstractPluginHelper implements HtmlParser<String> {

        private static PopoverAfterHtmlParser instance;
        public static final String TRIGGER = "trigger";

        private PopoverAfterHtmlParser() {
            arguments.put("owner", Collections.emptyList());
        }

        public static PopoverAfterHtmlParser getInstance() {
            if (instance == null)
                return instance = new PopoverAfterHtmlParser();
            return instance;
        }

        @Override
        public String parse(Element htmlElement1, Map<String, ?> template) {
            HTMLElement htmlElement = (HTMLElement) htmlElement1.parentNode;
            if (htmlElement.hasAttribute("owner")) {
                String owner = htmlElement.getAttribute("owner");
                Object ownerObj = template.get(owner);
                String uiField = htmlElement.getAttribute("pp");
                Popover popover = (Popover) template.get(uiField);
                popover.asElement().remove();
                if (ownerObj instanceof IsElement) {
                    popover.setOwner(((IsElement) ownerObj).asElement());
                }
                if (ownerObj instanceof HTMLElement) {
                    popover.setOwner((HTMLElement) ownerObj);
                }
                if (htmlElement.hasAttribute(TRIGGER)) {
                    String or = htmlElement.getAttribute(TRIGGER);
                    Trigger orValue = Trigger.lookUp.getValue(or);
                    if (orValue != null)
                        popover.setTrigger(orValue);
                }
            }
            htmlElement.remove();
            return null;
        }

        @Override
        public String getId() {
            return "dn-popover-after";
        }

        @Override
        public Class getClazz() {
            return Popover.class;
        }
    }

    @Override
    protected PopoverViewSlots getViewSlots() {
        return (PopoverViewSlots) super.getViewSlots();
    }

}

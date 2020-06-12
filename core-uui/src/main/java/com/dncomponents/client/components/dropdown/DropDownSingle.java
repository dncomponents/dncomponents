package com.dncomponents.client.components.dropdown;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.BaseComponentSingleSelection;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.close.CloseEvent;
import com.dncomponents.client.components.core.events.close.CloseHandler;
import com.dncomponents.client.components.core.events.close.HasCloseHandlers;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainRendererImpl;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.Map;

/**
 * @author nikolasavic
 * <p>
 */
public class DropDownSingle<T> extends BaseComponentSingleSelection<T, DropDownUi, DropDownItem<T>> implements
        HasOpenHandlers, HasCloseHandlers {

    MainRenderer<T> renderer = new MainRendererImpl<>();
    boolean menuVisible;
    HandlerRegistration clickOutHandlerRegistration;


    public DropDownSingle(DropDownUi ui) {
        super(ui);
        view.getRootView().addClickOnButton((ClickHandler) mouseEvent -> {
            showMenu(!menuVisible);
            if (menuVisible) {
                clickOutHandlerRegistration = view.getRootView().addClickOutOfButton(evt -> {
                    if (!DomUtil.isDescendant(this.asElement(), ((Element) evt.target))) {
                        this.clickOutHandlerRegistration.removeHandler();
                        this.clickOutHandlerRegistration = null;
                        this.showMenu(false);
                    }
                });
            }
        });
    }

    public DropDownSingle() {
        this(Ui.get().getDropDownUi());
    }

    private void fireOpenCloseEvent() {
        if (menuVisible)
            OpenEvent.fire(this, this);
        else
            CloseEvent.fire(this, this);

    }

    @Override
    public boolean setSelected(DropDownItem<T> tDropDownItem, boolean b, boolean fireEvent) {
        return super.setSelected(tDropDownItem, b, fireEvent);
    }

    public void setButtonContent(String content) {
        view.getRootView().setButtonContent(content);
    }

    public void setButtonContent(HTMLElement content) {
        view.getRootView().setButtonHtmlContent(content);
    }

    public void showMenu(boolean b) {
        view.getRootView().showList(b);
        menuVisible = b;
        fireOpenCloseEvent();
    }

    public void addItem(DropDownItem<T> item) {
        super.addItem(item);
        view.getRootView().addItem(item);
    }

    public void removeItem(DropDownItem<T> item) {
        super.removeItem(item);
        view.getRootView().removeItem(item);
    }

    public void addItems(T... items) {
//        for (T item : items) addItem(new DropDownItem<>(this, item));
    }


    @Override
    public HandlerRegistration addOpenHandler(OpenHandler handler) {
        return handler.addTo(asElement());
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler handler) {
        return handler.addTo(asElement());
    }

    public void setItemRenderer(MainRenderer<T> renderer) {
        this.renderer = renderer;
    }

    @Override
    protected DropDownUi getView() {
        return super.getView();
    }

    public static class DropDownHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static final String CONTENT = "content";

        private static DropDownSingle.DropDownHtmlParser instance;

        private DropDownHtmlParser() {
            tags.put(CONTENT, Collections.emptyList());
        }

        public static DropDownSingle.DropDownHtmlParser getInstance() {
            if (instance == null)
                return instance = new DropDownSingle.DropDownHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            DropDown dropDown;
            DropDownUi ui = getView(DropDown.class, htmlElement, elements);
            if (ui != null)
                dropDown = new DropDown(ui);
            else
                dropDown = new DropDown();
            if (htmlElement.hasAttribute(CONTENT)) {
                dropDown.setButtonContent(htmlElement.getAttribute(CONTENT));
            }
            NodeList<Element> elementsByTagName = htmlElement.getElementsByTagName(DropDownItem.is());
            for (int i = 0; i < elementsByTagName.length; i++) {
                DropDownItem dropDownItem = parseAccordionItem((HTMLElement) elementsByTagName.getAt(i), dropDown);
                dropDown.addItem(dropDownItem);
            }
            replaceAndCopy(htmlElement, dropDown);
            return dropDown;
        }

        public DropDownItem parseAccordionItem(HTMLElement node, DropDown dropDown) {
            String value = node.textContent;
            DropDownItem<Object> item = new DropDownItem(dropDown, value);
            return item;
        }


        @Override
        public String getId() {
            return "dn-dropdown";
        }

        @Override
        public Class getClazz() {
            return DropDownUi.class;
        }
    }

}
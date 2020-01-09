package com.dncomponents.client.components.dropdown;

import com.dncomponents.client.components.core.selectionmodel.helper.HasItemSelectionHandlers;
import com.dncomponents.client.components.core.selectionmodel.helper.ItemSelectionEvent;
import com.dncomponents.client.components.core.selectionmodel.helper.ItemSelectionHandler;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.BaseComponentSingleSelection;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemViewSlots;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import com.google.gwt.event.logical.shared.*;
import com.google.gwt.event.shared.HandlerRegistration;
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
        HasOpenHandlers, HasCloseHandlers, HasItemSelectionHandlers<DropDownItem<T>> {

    DropDownItem.DropDownItemRenderer<T> renderer = new DropDownItem.DropDownItemRenderer<T>() {
        @Override
        public void render(T userObject, DropDownItemViewSlots slots) {
            slots.getMainSlot().textContent = userObject + "";
        }
    };

    boolean menuVisible;

    public DropDownSingle(DropDownUi ui) {
        super(ui);
    }


    public DropDownSingle() {
        super(Ui.get().getDropDownUi());
        view.getRootView().addClickOnButton((ClickHandler) mouseEvent -> {
            showMenu(!menuVisible);
            fireOpenCloseEvent();
        });
        view.getRootView().addClickOutOfButton(() -> {
            if (menuVisible) {
                showMenu(false);
                fireOpenCloseEvent();
            }
        });
    }

    private void fireOpenCloseEvent() {
        if (menuVisible)
            OpenEvent.fire(this, this);
        else
            CloseEvent.fire(this, this);

    }

    @Override
    public boolean setSelected(DropDownItem<T> tDropDownItem, boolean b, boolean fireEvent) {
        ItemSelectionEvent.fire(this, tDropDownItem);
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
        return ensureHandlers().addHandler(OpenEvent.getType(), handler);
    }

    @Override
    public HandlerRegistration addCloseHandler(CloseHandler handler) {
        return ensureHandlers().addHandler(CloseEvent.getType(), handler);
    }

    public void setItemRenderer(DropDownItem.DropDownItemRenderer<T> renderer) {
        this.renderer = renderer;
    }

    @Override
    public HandlerRegistration addItemSelectionHandler(ItemSelectionHandler<DropDownItem<T>> handler) {
        return ensureHandlers().addHandler(ItemSelectionEvent.getType(), handler);
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
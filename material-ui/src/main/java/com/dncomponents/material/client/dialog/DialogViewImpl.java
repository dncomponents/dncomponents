package com.dncomponents.material.client.dialog;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.modal.DialogView;
import com.dncomponents.client.views.core.ui.modal.DialogViewSlots;
import com.dncomponents.client.components.core.events.Command;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class DialogViewImpl implements DialogView {

    public static final String VIEW_ID = "default";
    @UiField
    public HTMLElement root;
    @UiField
    public HTMLElement header;
    @UiField
    public HTMLElement body;
    @UiField
    public HTMLElement footer;
    @UiField
    public HTMLElement closeButton;
    @UiField
    public HTMLElement titleHeader;
    @UiStyle
    String showDialogStyle;

    HtmlBinder uiBinder = HtmlBinder.get(DialogViewImpl.class, this);

    public DialogViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    public DialogViewImpl(String templateContent) {
        uiBinder.setTemplateContent(templateContent);
        uiBinder.bind();
    }


    @Override
    public void setHeader(IsElement element) {
        header.innerHTML = "";
        header.appendChild(element.asElement());
    }

    @Override
    public void setContent(HTMLElement element) {
        body.innerHTML = "";
        body.appendChild(element);
    }

    @Override
    public void setTitle(String title) {
        titleHeader.innerHTML = title;
    }

    @Override
    public void setFooter(IsElement el) {
        footer.innerHTML = "";
        footer.appendChild(el.asElement());
    }

    @Override
    public void addCloseHandler(Command onCloseCmd) {
        final ClickHandler clkHandler = mouseEvent -> {
            mouseEvent.stopImmediatePropagation();
            onCloseCmd.execute();
        };
        DomUtil.addHandler(closeButton, clkHandler);
    }

    @Override
    public void show() {
        root.classList.add(showDialogStyle);
    }

    @Override
    public void hide() {
        root.classList.remove(showDialogStyle);
    }

    @Override
    public void addFooterElement(HTMLElement element) {
        footer.appendChild(element);
    }

    @Override
    public void clearFooter() {
        footer.innerHTML = "";
    }

    @Override
    public void setWidth(String width) {

    }

    @Override
    public void setHeight(String height) {

    }

    @Override
    public void setBackDrop(boolean backdrop) {

    }

    @Override
    public void setCloseOnEscape(boolean closeOnEscape) {

    }

    @Override
    public void setDraggable(boolean draggable) {

    }

    @Override
    public void setPosition(int top, int left) {

    }

    @Override
    public int getTopPosition() {
        return 0;
    }

    @Override
    public int getLeftPosition() {
        return 0;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    @Override
    public DialogViewSlots getViewSlots() {
        return slots;
    }

    DialogViewSlots slots = new DialogViewSlots() {
        @Override
        public HTMLElement getHeaderPanel() {
            return header;
        }

        @Override
        public HTMLElement getContentPanel() {
            return body;
        }

        @Override
        public HTMLElement getFooterPanel() {
            return footer;
        }
    };
}
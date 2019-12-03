package com.dncomponents.bootstrap.client.modal;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.*;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.modal.ModalDialogView;
import com.dncomponents.client.views.core.ui.modal.ModalDialogViewSlots;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import elemental2.dom.*;

/**
 * @author nikolasavic
 */
public class ModalDialogViewImpl implements ModalDialogView {

    @UiField
    public HTMLElement root;
    @UiField
    public HTMLElement modalDialogPanel;
    @UiField
    public HTMLElement header;
    @UiField
    public HTMLElement body;
    @UiField
    public HTMLElement footer;
    @UiField
    public HTMLElement closeButton;
    @UiField
    public HTMLElement closeHeader;
    @UiField
    public HTMLElement titleHeader;
    @UiField
    HTMLDivElement backDropDiv;
    private boolean backdrop;
    private boolean closeOnEscape;
    private boolean draggable;
    private HandlerRegistration backDrophandlerRegistration;
    private HandlerRegistration escapeHandlerRegistration;
    private HandlerRegistration mouseDownHandlerRegistration;
    HtmlBinder uiBinder = HtmlBinder.get(ModalDialogViewImpl.class, this);

    public ModalDialogViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    public ModalDialogViewImpl(String templateContent) {
        uiBinder.setTemplateContent(templateContent);
        uiBinder.bind();
        init();
    }

    double pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;
    double p;

    private void init() {
        backDropDiv.remove();
        modalDialogPanel.style.marginRight = CSSProperties.MarginRightUnionType.of(0);
        modalDialogPanel.style.marginLeft = CSSProperties.MarginLeftUnionType.of(0);
        asElement().style.overflow = "hidden";
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
            mouseEvent.stopPropagation();
            mouseEvent.stopImmediatePropagation();
            onCloseCmd.execute();
        };
        DomUtil.addHandler(closeHeader, clkHandler);
        DomUtil.addHandler(closeButton, clkHandler);
    }

    @Override
    public void show() {
        asElement().style.display = "block";
        asElement().classList.add("show");

        modalDialogPanel.style.top = ((DomGlobal.window.innerHeight / 2) - (modalDialogPanel.offsetHeight / 2)) + "px";
        modalDialogPanel.style.left = ((DomGlobal.window.innerWidth / 2) - (modalDialogPanel.offsetWidth / 2)) + "px";
        if (backdrop)
            DomUtil.addToBody(backDropDiv);
        if (closeOnEscape)
            root.focus();
    }

    @Override
    public void hide() {
        asElement().classList.remove("show");
        asElement().style.display = "none";
        if (backdrop)
            DomGlobal.document.body.removeChild(backDropDiv);
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
        DomUtil.setMinWidth(modalDialogPanel, width);
    }

    @Override
    public void setHeight(String height) {
        DomUtil.setMinHeight(modalDialogPanel, height);
    }


    @Override
    public void setBackDrop(boolean backdrop) {
        this.backdrop = backdrop;
        remove(backDrophandlerRegistration);
        if (backdrop) {
            backDrophandlerRegistration =
                    DomUtil.addHandler(backDropDiv, (ClickHandler) mouseEvent -> hide());
        }
    }


    @Override
    public void setCloseOnEscape(boolean closeOnEscape) {
        this.closeOnEscape = closeOnEscape;
        if (closeOnEscape) {
            root.setAttribute("tabindex", "-1");
            remove(escapeHandlerRegistration);
            escapeHandlerRegistration = DomUtil.addHandler(root, (KeyDownHandler) event -> {
                if (event.code.equals("Escape")) {
                    hide();
                }
            });
        } else {
            remove(escapeHandlerRegistration);
            root.removeAttribute("tabindex");
        }
    }


    @Override
    public void setDraggable(boolean draggable) {
        remove(mouseDownHandlerRegistration);
        this.draggable = draggable;
        if (this.draggable) {
            mouseDownHandlerRegistration = ((MouseDownHandler) e -> {
                e.preventDefault();
                pos3 = e.clientX;
                pos4 = e.clientY;
                p = (pos4 - modalDialogPanel.offsetTop);
                final HandlerRegistration mouseMoveRegistration = ((MouseMoveHandler) mm -> {
                    mm.preventDefault();
                    // calculate the new cursor position:
                    pos1 = pos3 - mm.clientX;
                    pos2 = pos4 - mm.clientY;
                    pos3 = mm.clientX;
                    pos4 = mm.clientY;
                    // set the element's new position:
                    modalDialogPanel.style.top = (pos4 - p) - 25 + "px";
                    modalDialogPanel.style.left = (modalDialogPanel.offsetLeft - pos1) + "px";
                }).addTo(asElement());
                final HandlerRegistration mouseUpRegistration = ((MouseUpHandler) mouseEvent -> {
                    remove(mouseMoveRegistration);
                }).addTo(DomGlobal.document);
            }).addTo(header);
        }
    }

    @Override
    public void setPosition(int top, int left) {
        modalDialogPanel.style.top = top + "px";
        modalDialogPanel.style.left = left + "px";
    }

    @Override
    public int getTopPosition() {
        final String top = modalDialogPanel.style.top;
        return Integer.parseInt(top.substring(0, top.length() - 2));
    }

    @Override
    public int getLeftPosition() {
        final String left = modalDialogPanel.style.left;
        return Integer.parseInt(left.substring(0, left.length() - 2));
    }

    private void remove(HandlerRegistration registration) {
        if (registration != null)
            registration.removeHandler();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }


    @Override
    public ModalDialogViewSlots getViewSlots() {
        return slots;
    }

    ModalDialogViewSlots slots = new ModalDialogViewSlots() {
        @Override
        public HTMLElement getHeaderPanel() {
            return titleHeader;
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
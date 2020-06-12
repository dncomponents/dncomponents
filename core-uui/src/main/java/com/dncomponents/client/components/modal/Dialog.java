package com.dncomponents.client.components.modal;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.views.Renderer;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.hide.HasHideHandlers;
import com.dncomponents.client.components.core.events.hide.HideEvent;
import com.dncomponents.client.components.core.events.hide.HideHandler;
import com.dncomponents.client.components.core.events.show.HasShowHandlers;
import com.dncomponents.client.components.core.events.show.ShowEvent;
import com.dncomponents.client.components.core.events.show.ShowHandler;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.modal.DialogView;
import com.dncomponents.client.views.core.ui.modal.DialogViewSlots;
import elemental2.dom.Element;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.Map;

/**
 * @author nikolasavic
 */
public class Dialog<T> extends BaseComponent<T, DialogView> implements HasShowHandlers, HasHideHandlers {

    private boolean backdrop = true;
    private boolean closeOnEscape = true;
    private boolean draggable = true;

    public Dialog() {
        this(Ui.get().getModalDialogView());
        bind();
    }

    public Dialog(DialogView view) {
        super(view);
        bind();
    }

    private void bind() {
        view.addCloseHandler(this::hide);
        setBackdrop(backdrop);
        setCloseOnEscape(closeOnEscape);
        setDraggable(draggable);
    }

    public void show() {
        DomUtil.addToBody(this);
        view.show();
        ShowEvent.fire(this, this);
    }

    public void hide() {
        view.hide();
        this.asElement().remove();
        HideEvent.fire(this, this);
    }

    public void setWidth(String width) {
        view.setWidth(width);
    }

    public void setHeight(String height) {
        view.setHeight(height);
    }

    @Override
    protected DialogView getView() {
        return super.getView();
    }

    @Override
    public HandlerRegistration addShowHandler(ShowHandler handler) {
        return handler.addTo(asElement());
    }


    @Override
    public HandlerRegistration addHideHandler(HideHandler handler) {
        return handler.addTo(asElement());
    }

    public interface ModalRenderer<T> extends Renderer<T, DialogViewSlots> {
    }

    public void setRenderer(ModalRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

    public void setHeader(SetElement se) {
        se.setHtml(getView().getViewSlots().getHeaderPanel());
    }

    public void setContent(SetElement se) {
        se.setHtml(getView().getViewSlots().getContentPanel());
    }

    public void setFooter(SetElement se) {
        se.setHtml(getView().getViewSlots().getFooterPanel());
    }

    public void setBackdrop(boolean backdrop) {
        this.backdrop = backdrop;
        view.setBackDrop(backdrop);
    }

    public void setCloseOnEscape(boolean closeOnEscape) {
        this.closeOnEscape = closeOnEscape;
        view.setCloseOnEscape(closeOnEscape);
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
        view.setDraggable(draggable);
    }

    public boolean isDraggable() {
        return draggable;
    }

    public boolean isBackdrop() {
        return backdrop;
    }

    public boolean isCloseOnEscape() {
        return closeOnEscape;
    }

    public void setPosition(int top, int left) {
        view.setPosition(top, left);
    }

    public int getTopPosition() {
        return view.getTopPosition();
    }

    public int getLeftPosition() {
        return view.getLeftPosition();
    }

    public static class ModalDialogHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static String HEADER_TAG = "header";
        private static String CONTENT_TAG = "content";
        private static String FOOTER_TAG = "footer";

        private static ModalDialogHtmlParser instance;

        private ModalDialogHtmlParser() {
            tags.put(HEADER_TAG, Collections.emptyList());
            tags.put(CONTENT_TAG, Collections.emptyList());
            tags.put(FOOTER_TAG, Collections.emptyList());

        }

        public static ModalDialogHtmlParser getInstance() {
            if (instance == null)
                return instance = new ModalDialogHtmlParser();
            return instance;
        }

        @Override
        public Dialog parse(Element html, Map<String, ?> elements) {
            Dialog modalDialog;

            DialogView view = getView(Dialog.class, html, elements);
            if (view != null)
                modalDialog = new Dialog(view);
            else
                modalDialog = new Dialog();

            NodeList<Element> titles = html.getElementsByTagName(HEADER_TAG);
            for (int i = 0; i < titles.length; i++) {
                modalDialog.getView().getViewSlots().getHeaderPanel().innerHTML = titles.getAt(i).innerHTML;
                break;
            }
            NodeList<Element> contents = html.getElementsByTagName(CONTENT_TAG);
            for (int i = 0; i < contents.length; i++) {
                modalDialog.getView().getViewSlots().getContentPanel().innerHTML = contents.getAt(i).innerHTML;
                break;
            }
            NodeList<Element> bottoms = html.getElementsByTagName(FOOTER_TAG);
            for (int i = 0; i < contents.length; i++) {
                modalDialog.getView().getViewSlots().getFooterPanel().innerHTML = bottoms.getAt(i).innerHTML;
                break;
            }
            replaceAndCopy(html, modalDialog);
            html.remove();
            return modalDialog;
        }

        @Override
        public String getId() {
            return "dn-modal-dialog";
        }

        @Override
        public Class getClazz() {
            return Dialog.class;
        }

    }

}
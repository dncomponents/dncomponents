package com.dncomponents.client.components.accordion;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.CanSelect;
import com.dncomponents.client.components.core.events.beforeselection.BeforeSelectionEvent;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemView;
import com.dncomponents.client.views.core.ui.accordion.AccordionItemViewSlots;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public class AccordionItem<T> extends BaseComponent<T, AccordionItemView> implements CanSelect {

    final Accordion<T> accordion;
    boolean selected;

    public AccordionItem(Accordion accordion) {
        super(accordion.getView().getAccordionItemView());
        this.accordion = accordion;
        setRenderer(accordion.accordionItemRenderer);
        bind();
    }

    public AccordionItem(Accordion accordion, AccordionItemView view) {
        super(view);
        this.accordion = accordion;
        setRenderer(accordion.accordionItemRenderer);
        bind();
    }

    public AccordionItem(Accordion accordion, String titleHtml, String contentHtml) {
        this(accordion, accordion.getView().getAccordionItemView());
        setTitle(titleHtml);
        setContent(contentHtml);
    }

    public AccordionItem(Accordion accordion, IsElement titleElement, IsElement contentElement) {
        this(accordion, accordion.getView().getAccordionItemView());
        setTitle(titleElement);
        setContent(contentElement);
    }

    public AccordionItem(Accordion accordion, HTMLElement titleElement, HTMLElement contentElement) {
        this(accordion, accordion.getView().getAccordionItemView());
        setTitle(titleElement);
        setContent(contentElement);
    }

    public AccordionItem(Accordion accordion, T userObject) {
        this(accordion, accordion.getView().getAccordionItemView());
        setUserObject(userObject);
    }

    public AccordionItem(Accordion accordion, T userObject, RenderAccordionItem<T> renderer) {
        this(accordion, accordion.getView().getAccordionItemView());
        setRenderer(renderer);
        setUserObject(userObject);
    }

    private void bind() {
        view.addItemSelectedHandler(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                onItemSelected();
            }
        });
    }

    void onItemSelected() {
        BeforeSelectionEvent.fire(accordion, this);
        accordion.setSelected(AccordionItem.this, !AccordionItem.this.isSelected(), true);
        SelectionEvent.fire(accordion, this);
    }

    @Override
    protected AccordionItemView getView() {
        return super.getView();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean b) {
        selected = b;
        view.select(b);
    }

    public void setTitle(String html) {
        view.setItemTitle(html);
    }

    public String getTitle() {
        return view.getTitle();
    }

    public String getContent() {
        return view.getContent();
    }

    public void setTitle(IsElement element) {
        view.setItemTitle(element.asElement());
    }

    public void setTitle(HTMLElement element) {
        view.setItemTitle(element);
    }

    public void setContent(String html) {
        view.setItemContent(html);
    }

    public void setContent(IsElement content) {
        view.setItemContent(content.asElement());
    }

    public void setContent(HTMLElement content) {
        view.setItemContent(content);
    }

    public interface RenderAccordionItem<T> extends Renderer<T, AccordionItemViewSlots> {
    }

    public void setRenderer(RenderAccordionItem<T> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    public AccordionItemViewSlots getViewSlots() {
        return (AccordionItemViewSlots) super.getViewSlots();
    }
}

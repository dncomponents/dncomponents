package com.dncomponents.client.components.accordion;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponentMultiSelection;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemIdTitle;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author nikolasavic
 */
public class Accordion<T> extends BaseComponentMultiSelection<T, AccordionUi, AccordionItem<T>> {

    AccordionItem.RenderAccordionItem<T> accordionItemRenderer;

    private boolean multiExpand = true;
    private boolean allClosed;

    public Accordion(AccordionUi ui) {
        super(ui);
    }

    public Accordion() {
        this(Ui.get().getAccordionUi());
    }

    public void addItem(AccordionItem<T> item) {
        super.addItem(item);
        view.getRootView().addItem(item);
        if (!allClosed && getItems().size() == 1)
            setSelected(getItems().get(0), true);
    }

    public void removeItem(AccordionItem<T> item) {
        super.removeItem(item);
        view.getRootView().removeItem(item);
    }

    @Override
    public void removeAllItems() {
        super.removeAllItems();
        view.getRootView().clearAll();
    }

    @Override
    public AccordionItem<T> createItem(T t) {
        return new AccordionItem<>(this, t);
    }

    boolean singleSelectionChanged;

    @Override
    public boolean setSelected(AccordionItem<T> item, boolean b, boolean fireEvent) {
        if (singleSelectionChanged)
            clearSelection(item);
        if (!allClosed && getSelection().size() == 1 && getSelection().get(0).equals(item) && !b)
            return false;
        else
            return super.setSelected(item, b, fireEvent);
    }

    private void clearSelection(AccordionItem<T> item) {
        if (selectionGroup.getSelectionMode() == DefaultMultiSelectionModel.SelectionMode.SINGLE) {
            singleSelectionChanged = false;
            getItems().forEach(new Consumer<AccordionItem<T>>() {
                @Override
                public void accept(AccordionItem<T> ai) {
                    if (ai != item)
                        setSelected(ai, false, false);
                }
            });
        }
    }

    public void setItemRenderer(AccordionItem.RenderAccordionItem<T> accordionItemRenderer) {
        this.accordionItemRenderer = accordionItemRenderer;
    }


    public boolean isMultiExpand() {
        return multiExpand;
    }

    public void setMultiExpand(boolean multiExpand) {
        this.multiExpand = multiExpand;
        singleSelectionChanged = !multiExpand;
        if (multiExpand)
            selectionGroup.setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        else
            selectionGroup.setSelectionMode(DefaultMultiSelectionModel.SelectionMode.SINGLE);
    }

    public boolean isAllClosed() {
        return allClosed;
    }

    public void setAllClosed(boolean allClosed) {
        this.allClosed = allClosed;
    }

    @Override
    protected AccordionUi getView() {
        return super.getView();
    }

    public static class AccordionHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static String TITLE_TAG = "title";
        private static String CONTENT_TAG = "content";

        private static AccordionHtmlParser instance;

        private AccordionHtmlParser() {
            tags.put(TITLE_TAG, Collections.emptyList());
            tags.put(CONTENT_TAG, Collections.emptyList());
        }

        public static AccordionHtmlParser getInstance() {
            if (instance == null)
                return instance = new AccordionHtmlParser();
            return instance;
        }

        @Override
        public Accordion parse(Element htmlElement, Map<String, ?> templateElement) {
            Accordion<ItemIdTitle> accordion = new Accordion<>();
            NodeList<Element> elementsByTagName = htmlElement.getElementsByTagName(ITEM);
            for (int i = 0; i < elementsByTagName.length; i++) {
                AccordionItem<ItemIdTitle> accordionItem =
                        parseAccordionItem((HTMLElement) elementsByTagName.getAt(i), accordion);
                accordion.addItem(accordionItem);
            }
            replaceAndCopy(htmlElement, accordion);
            return accordion;
        }

        @Override
        public String getId() {
            return "dn-accordion";
        }

        @Override
        public Class getClazz() {
            return Accordion.class;
        }

        public AccordionItem<ItemIdTitle> parseAccordionItem(HTMLElement node, Accordion accordion) {
            AccordionItem<ItemIdTitle> item = new AccordionItem(accordion);
            ItemIdTitle idItem = new ItemIdTitle();
            item.setUserObject(idItem);
            idItem.setId(getElementId(node));
            NodeList<Element> titles = node.getElementsByTagName(TITLE_TAG);
            for (int i = 0; i < titles.length; i++) {
                idItem.setTitle(titles.getAt(i).textContent);
                item.getViewSlots().getTitle().innerHTML = titles.getAt(i).textContent;
                titles.getAt(i).remove();
                break;
            }
            NodeList<Element> contents = node.getElementsByTagName(CONTENT_TAG);
            for (int i = 0; i < contents.length; i++) {
                idItem.setContent(contents.getAt(i).innerHTML);
                item.getViewSlots().getContent().innerHTML = contents.getAt(i).innerHTML;
                break;
            }
            return item;
        }
    }

    @Override
    public String toString() {
        return "accordion: tag : " + AccordionHtmlParser.getInstance().getId();
    }
}

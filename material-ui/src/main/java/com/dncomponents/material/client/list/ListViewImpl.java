package com.dncomponents.material.client.list;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.KeyDownHandler;
import com.dncomponents.client.dom.handlers.ScrollHandler;
import com.dncomponents.client.views.core.ui.list.ListView;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class ListViewImpl implements ListView {

    @UiField
    public HTMLElement root;
    @UiField
    String rowHeight;
    double currentScrollTop;
    @UiStyle
    protected String scrollableStyle;

    public ListViewImpl() {
    }

    public ListViewImpl(HTMLTemplateElement listMain) {
        HtmlBinder uiBinder = HtmlBinder.get(ListViewImpl.class, this);
        uiBinder.setTemplateElement(listMain);
        uiBinder.bind();
        init();
    }

    protected void init() {
        addScrollHandler(event -> currentScrollTop = getScrollTop());
    }

    @Override
    public void addItem(Element element) {
        getItemPanel().appendChild(element);
    }

    @Override
    public void clear() {
        getItemPanel().innerHTML = "";
    }

    @Override
    public void setScrollable(boolean b) {
        if (b)
            getItemPanel().classList.add(scrollableStyle);
        else
            getItemPanel().classList.remove(scrollableStyle);
    }

    @Override
    public void setScrollHeight(String height) {
        DomUtil.setMaxHeight(root, height);
    }

    @Override
    public HandlerRegistration addScrollHandler(ScrollHandler scrollHandler) {
        return scrollHandler.addTo(getItemPanel());
    }

    @Override
    public double getScrollTop() {
        return getScrollPanel().scrollTop;
    }

    @Override
    public void resetScrollTop(Double value) {
        if (value == null)
            getScrollPanel().scrollTop = currentScrollTop;
        else
            getScrollPanel().scrollTop = value;
    }

    @Override
    public HTMLElement getScrollPanel() {
        return root;
    }

    @Override
    public HTMLElement createEmptyRow() {
        return DomUtil.createLi();
    }

    @Override
    public int getRowHeight() {
        return Integer.parseInt(rowHeight);
    }

    @Override
    public HandlerRegistration addKeyDownHandler(KeyDownHandler keyDownHandler) {
        return keyDownHandler.addTo(root);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    protected HTMLElement getItemPanel() {
        return root;
    }
}
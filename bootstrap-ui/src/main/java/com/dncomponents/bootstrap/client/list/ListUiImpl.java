package com.dncomponents.bootstrap.client.list;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.ScrollHandler;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.ui.list.ListCellCheckBoxView;
import com.dncomponents.client.views.core.ui.list.ListUi;
import com.dncomponents.client.views.core.ui.list.ListView;
import com.google.gwt.core.client.GWT;
import elemental2.dom.Event;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class ListUiImpl implements ListUi {


    @UiField("list-main")
    HTMLTemplateElement listMain;

    @UiField("list-item")
    public HTMLTemplateElement listItem;

    @UiField("list-item-checkbox")
    HTMLTemplateElement listItemCheckbox;

    protected HtmlBinder uiBinder = HtmlBinder.get(ListUiImpl.class, this);

    ListView listView;

    public ListUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public ListUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        getRootView().addScrollHandler(new ScrollHandler() {
            @Override
            public void onScroll(Event event) {
                GWT.log(getRootView().getScrollTop() + "");
            }
        });
    }

    @Override
    public BaseCellView getListCellView() {
        return new ListCellViewImpl(listItem);
    }

    @Override
    public ListCellCheckBoxView getListCheckBoxView() {
        return new ListCellCheckBoxViewImpl(listItemCheckbox);
    }

    @Override
    public ListView getRootView() {
        if (listView == null)
            listView = new ListViewImpl(listMain);
        return listView;
    }
}
package com.dncomponents.material.client.list;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.ui.list.ListCellCheckBoxView;
import com.dncomponents.client.views.core.ui.list.ListUi;
import com.dncomponents.client.views.core.ui.list.ListView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class ListUiImpl implements ListUi {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTemplateElement listMain;
    @UiField
    public HTMLTemplateElement listItem;
    @UiField
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
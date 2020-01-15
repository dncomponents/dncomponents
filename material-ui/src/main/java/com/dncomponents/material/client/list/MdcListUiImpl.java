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
public class MdcListUiImpl implements ListUi {


    @UiField("list-main")
    HTMLTemplateElement listMain;

    @UiField("list-item")
    public HTMLTemplateElement listItem;

    @UiField("list-item-checkbox")
    HTMLTemplateElement listItemCheckbox;

    protected HtmlBinder uiBinder = HtmlBinder.get(MdcListUiImpl.class, this);

    ListView listView;

    public MdcListUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public MdcListUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public BaseCellView getListCellView() {
        return new MdcListCellViewImpl(listItem);
    }

    @Override
    public ListCellCheckBoxView getListCheckBoxView() {
        return new MdcListCellCheckBoxViewImpl(listItemCheckbox);
    }

    @Override
    public ListView getRootView() {
        if (listView == null)
            listView = new MdcListViewImpl(listMain);
        return listView;
    }
}
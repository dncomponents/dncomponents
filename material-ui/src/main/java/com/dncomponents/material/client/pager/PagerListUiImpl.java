package com.dncomponents.material.client.pager;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.pager.PagerItemView;
import com.dncomponents.client.views.core.ui.pager.PagerListUi;
import com.dncomponents.client.views.core.ui.pager.PagerWithListView;
import elemental2.dom.HTMLTemplateElement;

public class PagerListUiImpl implements PagerListUi {

    @UiField("pager-item")
    HTMLTemplateElement pagerItem;
    @UiField
    HTMLTemplateElement pager;

    HtmlBinder uiBinder = HtmlBinder.get(PagerListUiImpl.class, this);


    public PagerListUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public PagerItemView getPagerItemView() {
        return new PagerItemViewImpl(pagerItem);
    }

    PagerWithListView pagerWithListView;

    @Override
    public PagerWithListView getRootView() {
        if (pagerWithListView == null)
            pagerWithListView = new PagerWithListViewImpl(pager);
        return pagerWithListView;
    }
}

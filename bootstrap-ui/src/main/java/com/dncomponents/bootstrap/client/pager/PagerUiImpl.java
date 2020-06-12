package com.dncomponents.bootstrap.client.pager;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.pager.PagerItemView;
import com.dncomponents.client.views.core.ui.pager.PagerUi;
import com.dncomponents.client.views.core.ui.pager.PagerView;
import elemental2.dom.HTMLTemplateElement;

public class PagerUiImpl implements PagerUi {

    @UiField
    HTMLTemplateElement pagerItem;
    @UiField
    HTMLTemplateElement pager;

    HtmlBinder uiBinder = HtmlBinder.get(PagerUiImpl.class, this);


    public PagerUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public PagerItemView getPagerItemView() {
        return new PagerItemViewImpl(pagerItem);
    }

    PagerView pagerView;

    @Override
    public PagerView getRootView() {
        if (pagerView == null)
            pagerView = new PagerViewImpl(pager);
        return pagerView;
    }
}

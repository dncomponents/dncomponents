package com.dncomponents.bootstrap.client.pager;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.pager.PagerItemView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class PagerItemViewImpl implements PagerItemView {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement textPanel;

    HtmlBinder uiBinder = HtmlBinder.get(PagerItemViewImpl.class, this);


    public PagerItemViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setText(String str) {
        textPanel.innerHTML = str;
    }

    @Override
    public void setActive(boolean b) {
        if (b)
            asElement().classList.add("active");
        else
            asElement().classList.remove("active");
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}

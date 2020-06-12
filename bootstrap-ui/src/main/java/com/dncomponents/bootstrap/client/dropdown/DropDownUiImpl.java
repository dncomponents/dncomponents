package com.dncomponents.bootstrap.client.dropdown;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import com.dncomponents.client.views.core.ui.dropdown.DropDownView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class DropDownUiImpl implements DropDownUi {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTemplateElement dropdown;
    @UiField
    HTMLTemplateElement dropDownItem;

    DropDownView dropDownView;

    HtmlBinder uiBinder = HtmlBinder.get(DropDownUiImpl.class, this);


    public DropDownUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public DropDownItemView getDropDownItemView() {
        return new DropDownItemViewImpl(dropDownItem);
    }

    @Override
    public DropDownView getRootView() {
        if (dropDownView == null)
            dropDownView = new DropDownViewImpl(dropdown);
        return dropDownView;
    }
}

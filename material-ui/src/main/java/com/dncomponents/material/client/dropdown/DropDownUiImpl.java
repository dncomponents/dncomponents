package com.dncomponents.material.client.dropdown;

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

    @UiField("dropdown")
    HTMLTemplateElement dropdown;
    @UiField("dropdown-item")
    HTMLTemplateElement dropdownItem;

    DropDownView dropDownView;

    HtmlBinder uiBinder = HtmlBinder.get(DropDownUiImpl.class, this);


    public DropDownUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public DropDownItemView getDropDownItemView() {
        return new DropDownItemViewImpl(dropdownItem);
    }

    @Override
    public DropDownView getRootView() {
        if (dropDownView == null)
            dropDownView = new DropDownViewImpl(dropdown);
        return dropDownView;
    }
}

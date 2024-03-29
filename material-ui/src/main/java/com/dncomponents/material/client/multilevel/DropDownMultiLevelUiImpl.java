package com.dncomponents.material.client.multilevel;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.dropdown.*;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class DropDownMultiLevelUiImpl implements DropDownMultiLevelUi {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLTemplateElement dropdown;
    @UiField
    HTMLTemplateElement dropdownItem;
    @UiField
    HTMLTemplateElement dropdownItemHasChildren;
    @UiField
    HTMLTemplateElement dropdownPanelHasChildren;

    DropDownView dropDownView;

    HtmlBinder uiBinder = HtmlBinder.create(DropDownMultiLevelUiImpl.class, this);


    public DropDownMultiLevelUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }


    @Override
    public DropDownItemView getDropDownItemView() {
        return new DropDownItemMultiLevelViewImpl(dropdownItem);
    }

    @Override
    public DropDownItemMultiLevelParentView getDropDownItemMultiLevelParentView() {
        return new DropDownItemMultiLevelParentViewImpl(dropdownItemHasChildren);
    }

    @Override
    public DropDownTreeNodePanelView getDropDownTreeNodePanelView() {
        return new DropDownTreeNodePanelViewImpl(dropdownPanelHasChildren);
    }

    @Override
    public DropDownView getRootView() {
        if (dropDownView == null)
            dropDownView = new DropDownMultilevelViewImpl(dropdown);
        return dropDownView;
    }
}

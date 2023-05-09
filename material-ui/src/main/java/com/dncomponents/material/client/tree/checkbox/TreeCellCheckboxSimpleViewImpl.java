package com.dncomponents.material.client.tree.checkbox;

import com.dncomponents.UiField;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxSimpleView;
import com.dncomponents.material.client.tree.basic.TreeCellViewImpl;
import com.dncomponents.client.components.core.events.value.HasValue;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
public class TreeCellCheckboxSimpleViewImpl extends TreeCellViewImpl implements TreeCellCheckboxSimpleView {

    @UiField
    CheckBox checkBox;

    HtmlBinder uiBinder = HtmlBinder.create(TreeCellCheckboxSimpleViewImpl.class, this);


    public TreeCellCheckboxSimpleViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        DomUtil.addHandler(checkBox.asElement(), new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                mouseEvent.stopImmediatePropagation();
            }
        });

    }

    @Override
    public HasValue<Boolean> getCheckBox() {
        return checkBox;
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        checkBox.setValue(b);
    }
}

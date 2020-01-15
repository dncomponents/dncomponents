package com.dncomponents.bootstrap.client.tree.checkbox;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.tree.basic.TreeCellParentViewImpl;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxParentView;
import com.google.gwt.user.client.ui.HasValue;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TreeCellCheckboxParentViewImpl extends TreeCellParentViewImpl implements TreeCellCheckboxParentView {

    @UiField
    CheckBox checkBox;

    HtmlBinder uiBinder = HtmlBinder.get(TreeCellCheckboxParentViewImpl.class, this);

    public TreeCellCheckboxParentViewImpl(HTMLTemplateElement templateElement) {
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
    public void setIndeterminate(boolean b) {
        checkBox.setIndeterminate(b);
    }

    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        checkBox.setValue(b);
    }
}

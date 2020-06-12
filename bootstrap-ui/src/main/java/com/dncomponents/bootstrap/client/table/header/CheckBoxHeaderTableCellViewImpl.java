package com.dncomponents.bootstrap.client.table.header;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.views.core.ui.table.headers.CheckBoxHeaderTableCellView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class CheckBoxHeaderTableCellViewImpl implements CheckBoxHeaderTableCellView {

    @UiField
    public HTMLElement textPanel;

    @UiField
    CheckBox checkBox;

    @UiField
    HTMLElement root;

    HtmlBinder uiBinder = HtmlBinder.get(CheckBoxHeaderTableCellViewImpl.class, this);

    public CheckBoxHeaderTableCellViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public CheckBoxHeaderTableCellViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HasValue<Boolean> getCheckBox() {
        return checkBox;
    }

    @Override
    public void setText(String text) {
        textPanel.innerHTML = text;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}
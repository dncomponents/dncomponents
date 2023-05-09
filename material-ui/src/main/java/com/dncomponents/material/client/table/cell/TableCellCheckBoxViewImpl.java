package com.dncomponents.material.client.table.cell;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.table.columnclasses.checkboxcolumn.TableCellCheckBoxView;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.cell.BaseCellViewImpl;
import com.dncomponents.client.components.core.events.value.HasValue;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@Component
public class TableCellCheckBoxViewImpl extends BaseCellViewImpl implements TableCellCheckBoxView {

    @UiField
    CheckBox checkBox;

    HtmlBinder uiBinder = HtmlBinder.create(TableCellCheckBoxViewImpl.class, this);

    public TableCellCheckBoxViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HasValue<Boolean> getCheckbox() {
        return checkBox;
    }
}

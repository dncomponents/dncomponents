package com.dncomponents.bootstrap.client.table.cell;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.cell.BaseCellViewImpl;
import com.dncomponents.client.components.checkbox.CheckBox;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.table.columnclasses.checkboxcolumn.TableCellCheckBoxView;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TableCellCheckBoxViewImpl extends BaseCellViewImpl implements TableCellCheckBoxView {

    @UiField
    CheckBox checkBox;

    HtmlBinder uiBinder = HtmlBinder.get(TableCellCheckBoxViewImpl.class, this);

    public TableCellCheckBoxViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public HasValue<Boolean> getCheckbox() {
        return checkBox;
    }
}
package com.dncomponents.client.components.table.columnclasses;

import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.components.textbox.DateBox;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;
/**
 * @author nikolasavic
 */
public class TableCellDate<T> extends TableCell<T, Date> {

    private DateTimeFormat format;

    public TableCellDate() {
        setFormat(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT));
        init();
    }

    public TableCellDate(DateTimeFormat format) {
        setFormat(format);
        init();
    }

    private void init() {
        //default renderer
        setRenderer(r -> {
            if (r.value == null)
                r.valuePanel.innerHTML = "N/D";
            else
                r.valuePanel.innerHTML = format.format(r.value);
        });
        //default cell editor
        DateBox dateBox = new DateBox();
        dateBox.setDateTimeFormat(format);
        setCellEditor(new DefaultCellEditor(dateBox));
    }


    public TableCellDate<T> setFormat(DateTimeFormat format) {
        this.format = format;
        return this;
    }
}
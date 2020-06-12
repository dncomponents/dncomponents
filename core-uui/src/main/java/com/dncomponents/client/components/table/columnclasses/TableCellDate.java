package com.dncomponents.client.components.table.columnclasses;

import com.dncomponents.client.components.core.DefaultCellEditor;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.textbox.DateBox;
import elemental2.core.JsDate;

import java.util.Date;

/**
 * @author nikolasavic
 */
public class TableCellDate<T> extends TableCell<T, Date> {

    //todo set format

    public TableCellDate() {
        init();
    }

    private void init() {
        //default renderer
        setRenderer(r -> {
            if (r.value == null)
                r.valuePanel.innerHTML = "N/D";
            else {
                final String s = new JsDate(((double) value.getTime())).toLocaleDateString();
                r.valuePanel.innerHTML = new JsDate(((double) value.getTime())).toLocaleDateString();
            }
        });
        //default cell editor
        DateBox dateBox = new DateBox();
        setCellEditor(new DefaultCellEditor(dateBox));
    }
}
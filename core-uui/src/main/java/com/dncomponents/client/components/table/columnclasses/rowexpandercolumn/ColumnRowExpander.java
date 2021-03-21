package com.dncomponents.client.components.table.columnclasses.rowexpandercolumn;


import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.TableCellRowExpander;
import com.dncomponents.client.components.core.RowDetailsRenderer;
import com.dncomponents.client.components.table.header.HeaderTableTextCell;

/**
 * Created by nikolasavic
 */
public class ColumnRowExpander<T> extends ColumnConfig<T, Object> {

    RowDetailsRenderer<T> rowDetailsRenderer;

    public ColumnRowExpander(RowDetailsRenderer<T> rowDetailsRenderer) {
        this();
        this.rowDetailsRenderer = rowDetailsRenderer;
    }

    public ColumnRowExpander() {
        super(t -> t);
        this.setColumnWidth("15px");
        this.setHeaderCellFactory(HeaderTableTextCell::new);
        this.setCellFactory(c -> new TableCellRowExpander<T, Object>().setRowDetailsRenderer(rowDetailsRenderer));
    }

    public ColumnRowExpander<T> setRowDetailsRenderer(RowDetailsRenderer<T> rowDetailsRenderer) {
        this.rowDetailsRenderer = rowDetailsRenderer;
        return this;
    }
}

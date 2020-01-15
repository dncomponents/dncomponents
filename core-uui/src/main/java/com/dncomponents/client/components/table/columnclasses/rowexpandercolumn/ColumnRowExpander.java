package com.dncomponents.client.components.table.columnclasses.rowexpandercolumn;


import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.TableCellRowExpander;
import com.dncomponents.client.components.table.header.HeaderTableTextCell;

/**
 * Created by nikolasavic
 */
public class ColumnRowExpander<T> extends ColumnConfig<T, Object> {

    public ColumnRowExpander() {
        super(t -> (t));
        this.setColumnWidth("15px");
        this.setHeaderCellFactory(HeaderTableTextCell::new);
        builder = new TableCellRowExpander.RowExpanderBuilder<>();
        this.setCellFactory(c -> new TableCellRowExpander<T, Object>().initWithBuilder(getCellBuilder()));
    }

    @Override
    public TableCellRowExpander.RowExpanderBuilder<T, Object> getCellBuilder() {
        return (TableCellRowExpander.RowExpanderBuilder<T, Object>) super.getCellBuilder();
    }
}
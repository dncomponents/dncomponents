package com.dncomponents.client.components;

import com.dncomponents.client.components.table.BaseTableCell;
import com.dncomponents.client.components.table.footer.FooterCellRenderer;
import com.dncomponents.client.views.core.pcg.cell.CellView;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractFooterCell<T, M> extends BaseTableCell<T, M, CellView> {


    protected FooterCellRenderer<T, M> cellRenderer;

    @Override
    public void draw() {
        if (cellRenderer != null)
            cellRenderer.setValue(cellView.asElement(), this);
    }

    public AbstractFooterCell<T, M> setCellRenderer(FooterCellRenderer<T, M> cellRenderer) {
        this.cellRenderer = cellRenderer;
        return this;
    }

    public List<M> getColumnItems() {
        return getOwner().getRowsData()
                .stream()
                .map(t -> getCellConfig()
                        .getFieldGetter()
                        .apply(t))
                .collect(Collectors.toList());
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getFooterCellView();
    }

}

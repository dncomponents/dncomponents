package com.dncomponents.client.components;

import com.dncomponents.client.components.core.RowDetailsRenderer;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.table.columnclasses.rowexpandercolumn.RowDetailsCellView;
import com.dncomponents.client.dom.handlers.ClickHandler;
import elemental2.dom.HTMLElement;

/**
 * Created by nikolasavic
 */
public class TableCellRowExpander<T, M> extends TableCell<T, M> {

    public static boolean singleExpand = true;
    boolean toggle = false;
    HTMLElement insertedRow;

    {
        setEditable(false);
    }


    private RowDetailsRenderer<T> rowDetailsRenderer;

    public TableCellRowExpander() {
    }


    @Override
    public void draw() {
        toggle = getOwner().rowExpanderList.contains(getModel());
        getCellView().setOpened(toggle);
        if (toggle)
            addRowDetailsPanel();
        else
            removeRowDetailsPanel();
    }

    @Override
    protected void renderView() {

    }

    @Override
    protected void bind() {
        super.bind();
        ((ClickHandler) mouseEvent -> {
            toggle = !toggle;
            if (singleExpand)
                removeAllExpandedTableRows();
            if (toggle) {
                getOwner().rowExpanderList.add(getModel());
                addRowDetailsPanel();
            } else {
                getOwner().rowExpanderList.remove(getModel());
                removeRowDetailsPanel();
            }
        }).addTo(asElement());
    }

    private void removeAllExpandedTableRows() {
        for (RowTable<T> row : getOwner().getRowCells(getOwner().rowExpanderList)) {
            if (row != null)
                for (TableCell cell : row.cells) {
                    if (cell instanceof TableCellRowExpander) {
                        ((TableCellRowExpander) cell).removeRowDetailsPanel();
                    }
                }
        }
        getOwner().rowExpanderList.clear();
    }

    private void setOpened(boolean b) {
        this.toggle = b;
        getCellView().setOpened(b);
    }

    private void addRowDetailsPanel() {
        insertedRow = getOwner()
                .getView()
                .getRootView()
                .insertAfter(getRowTable(), getOwner().getColumnConfigs().size());
        rowDetailsRenderer.render(model, insertedRow);
        setOpened(true);
    }

    private void removeRowDetailsPanel() {
        if (insertedRow != null)
            insertedRow.remove();
        setOpened(false);
    }

    @Override
    public RowDetailsCellView getCellView() {
        return (RowDetailsCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTableCellRowExpanderView();
    }

    public TableCellRowExpander<T, M> setRowDetailsRenderer(RowDetailsRenderer<T> rowDetailsPanel) {
        this.rowDetailsRenderer = rowDetailsPanel;
        return this;
    }

    @Override
    public <C extends BaseCell<T, M>> C initWithBuilder(BaseCellBuilder builder) {
        if (((RowExpanderBuilder) builder).rowDetailsRenderer != null)
            this.rowDetailsRenderer = ((RowExpanderBuilder) builder).rowDetailsRenderer;
        return super.initWithBuilder(builder);

    }

    public static class RowExpanderBuilder<T, M> extends TableCell.Builder<T, M> {
        private RowDetailsRenderer<T> rowDetailsRenderer;

        public RowExpanderBuilder<T, M> setRowDetailsRenderer(RowDetailsRenderer<T> rowDetailsRenderer) {
            this.rowDetailsRenderer = rowDetailsRenderer;
            return this;
        }
    }

}
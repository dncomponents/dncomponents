package com.dncomponents.client.components.table.header;

import com.dncomponents.client.views.core.ui.table.headers.HeaderTableSortCellView;

import java.util.ArrayList;

/**
 * Created by nikolasavic
 */
public class HeaderTableSortCell extends HeaderTableTextCell implements HeaderTableSortCellView.SortPresenter {

    private HeaderSorting headerSorting;

    public HeaderTableSortCell() {
    }

    public HeaderTableSortCell(HeaderTableSortCellView headerCellView) {
        super(headerCellView);
    }

    public HeaderTableSortCell(String name) {
        setText(name);
    }

    @Override
    protected void bind() {
        getCellView().setSortPresenter(this);
        headerCellHolder.addSortHandler(event -> {
            updateView(event.getByColumn(getCellConfig()));
            int index = new ArrayList<>(event.getModifiers()).indexOf(headerSorting);
            //Updates sorting orders for multiple sort
            getCellView().setSortIconText((event.getModifiers().size() > 1) && index != -1 ? index + 1 + "" : "");
        });
    }

    public void setHeaderCellHolder(HeaderCellHolder headerCellHolder) {
        this.headerCellHolder = headerCellHolder;
    }

    @Override
    public HeaderTableSortCellView getCellView() {
        return (HeaderTableSortCellView) super.getCellView();
    }

    /**
     * Sorts ascending {@link SortingDirection#ASCENDING}<p>
     * Sorts descending {@link SortingDirection#DESCENDING}<p>
     * Removes sorting {@code null}
     *
     * @param direction
     */
    @Override
    public void sort(SortingDirection direction) {
        if (headerSorting == null)
            headerSorting = new HeaderSorting(getCellConfig(), direction);
        else
            headerSorting.setSortingDirection(direction);
        headerCellHolder.sorted(headerSorting);
    }

    /**
     * Updates cell view if HeaderSorting is present in event's headers sorting list.
     * Otherwise set activity to false.
     *
     * @param header
     */
    private void updateView(HeaderSorting header) {
        this.headerSorting = header;
        getCellView().setSorted(header == null ? null : header.getSortingDirection());
        getCellView().setActive(header != null);
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getHeaderTableSortCellView();
    }


    @Override
    public HeaderTableSortCell setText(String columnName) {
        return (HeaderTableSortCell) super.setText(columnName);
    }
}
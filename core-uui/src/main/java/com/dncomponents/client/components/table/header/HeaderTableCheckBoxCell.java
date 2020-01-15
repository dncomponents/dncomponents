package com.dncomponents.client.components.table.header;

import com.dncomponents.client.views.core.ui.table.headers.CheckBoxHeaderTableCellView;

/**
 * Created by nikolasavic
 */
public class HeaderTableCheckBoxCell extends HeaderTableTextCell {

    public HeaderTableCheckBoxCell() {
    }

    public HeaderTableCheckBoxCell(CheckBoxHeaderTableCellView headerCellWidget) {
        super(headerCellWidget);
    }

    @Override
    protected void bind() {
        getCellView().getCheckBox().addValueChangeHandler(event -> getOwner().getSelectionModel().selectAll(event.getValue(), true));
        getOwner().getSelectionModel().addSelectionHandler(event -> draw());
    }


    @Override
    public void draw() {
        getCellView().getCheckBox().setValue(getOwner().getSelectionModel().isAllSelected(), false);
    }

    @Override
    protected CheckBoxHeaderTableCellView getCellView() {
        return (CheckBoxHeaderTableCellView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getCheckBoxHeaderCellView();
    }
}

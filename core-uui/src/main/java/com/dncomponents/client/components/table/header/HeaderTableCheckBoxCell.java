package com.dncomponents.client.components.table.header;

import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.table.headers.CheckBoxHeaderTableCellView;
import elemental2.dom.MouseEvent;

/**
 * Created by nikolasavic
 */
public class HeaderTableCheckBoxCell extends HeaderTableTextCell {

    public HeaderTableCheckBoxCell() {
    }

    public HeaderTableCheckBoxCell(CheckBoxHeaderTableCellView view) {
        super(view);
    }

    @Override
    protected void bind() {
        getCellView().getCheckBox().addValueChangeHandler((ValueChangeEvent<Boolean> event) -> getOwner().getSelectionModel().selectAll(event.getValue(), true));
        getOwner().getSelectionModel().addSelectionHandler(event -> draw());
        new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                getCellView().getCheckBox().setValue(!getCellView().getCheckBox().getValue(),true);
            }
        }.addTo(getCellView().asElement());

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

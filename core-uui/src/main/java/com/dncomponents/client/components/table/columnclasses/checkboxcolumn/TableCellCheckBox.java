package com.dncomponents.client.components.table.columnclasses.checkboxcolumn;

import com.dncomponents.client.components.list.ListTreeMultiSelectionModel;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.dom.handlers.KeyDownHandler;
import elemental2.dom.KeyboardEvent;

/**
 * Created by nikolasavic
 */
public class TableCellCheckBox extends TableCell {

    public TableCellCheckBox() {
    }

    
    @Override
    protected void setOwner(AbstractCellComponent owner) {
        super.setOwner(owner);
        ((ListTreeMultiSelectionModel) getOwner().getSelectionModel()).setNavigator(false);
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckbox().addValueChangeHandler(
                event -> getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true));
        getCellView().addKeyDownEvent(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyboardEvent event) {
                if (event.key.equals("space")) {
                    getCellView().getCheckbox().setValue(!getCellView().getCheckbox().getValue(), true);
                }
            }
        });
        getOwner().getSelectionModel().addSelectionHandler(event -> draw());
    }

    //TODO do we need this ?
    @Override
    public void draw() {
        getCellView().getCheckbox().setValue(getOwner().getSelectionModel().isSelected(getModel()), false);
    }

    @Override
    protected void setSelection() {
        setSelectionBase();
    }

    @Override
    public TableCellCheckBoxView getCellView() {
        return (TableCellCheckBoxView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTableCellCheckBoxView();
    }
}

package com.dncomponents.client.components.list;

import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.views.core.ui.list.ListCellCheckBoxView;

/**
 * Created by nikolasavic
 */
public class ListCellCheckbox<T, M> extends ListCell<T, M> {

    public ListCellCheckbox() {
    }

    public ListCellCheckbox(ListCellCheckBoxView view) {
        super(view);
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckbox().addValueChangeHandler(new ValueChangeHandler<Boolean>() {
            @Override
            public void onValueChange(ValueChangeEvent<Boolean> event) {
                getOwner().getSelectionModel()
                        .setSelected(getModel(), event.getValue(), true);
            }
        });
        getOwner().getSelectionModel().setNavigator(false);
    }

    @Override
    public ListCellCheckBoxView getCellView() {
        return (ListCellCheckBoxView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getListCheckBoxView();
    }
}
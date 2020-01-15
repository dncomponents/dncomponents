package com.dncomponents.client.components.list;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.dncomponents.client.views.core.ui.list.ListUi;

/**
 * Created by nikolasavic
 */
public class ListCell<T, M> extends BaseCell<T, M> {

    public ListCell() {
    }

    public ListCell(BaseCellView cellView) {
        super(cellView);
    }

    @Override
    public ListData<T, M> getOwner() {
        return (ListData<T, M>) super.getOwner();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getListCellView();
    }


    @Override
    protected ListUi getUi() {
        return (ListUi) super.getUi();
    }

    protected ListCell(BaseCellBuilder<T, M, ?> builder) {
        super(builder);
    }

    public static class Builder<T, M> extends BaseCellBuilder<T, M, Builder<T, M>> {
        @Override
        public ListCell<T, M> build() {
            return new ListCell<>(this);
        }
    }

}
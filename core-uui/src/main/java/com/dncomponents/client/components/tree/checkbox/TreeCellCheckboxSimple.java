package com.dncomponents.client.components.tree.checkbox;

import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.tree.TreeCellSimple;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxSimpleView;

/**
 * Created by nikolasavic
 */
public class TreeCellCheckboxSimple<T, M> extends TreeCellSimple<T, M> {

    public TreeCellCheckboxSimple() {
    }

    public TreeCellCheckboxSimple(TreeCellCheckboxSimpleView treeCellView) {
        super(treeCellView);
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckBox().addValueChangeHandler((ValueChangeEvent<Boolean> event) ->
                getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true)
        );
//        getOwner().getSelectionModel().setNavigator(false);
    }

    @Override
    public TreeCellCheckboxSimpleView getCellView() {
        return (TreeCellCheckboxSimpleView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeCellCheckBoxView(icon);
    }

}

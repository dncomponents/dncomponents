package com.dncomponents.client.components.tree.checkbox;

import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.tree.AbstractTreeCell;
import com.dncomponents.client.components.tree.TreeCellSimple;
import com.dncomponents.client.components.tree.TreeNode;
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

    protected TreeCellCheckboxSimple(BaseCellBuilder<TreeNode<T>, M, ?> builder) {
        super(builder);
    }

    public static abstract class Builder<T, M> extends AbstractTreeCell.Builder<T, M> {
        @Override
        public TreeCellCheckboxSimple<T, M> build() {
            return new TreeCellCheckboxSimple<>(this);
        }
    }


}

package com.dncomponents.client.components.tree.checkbox;

import com.dncomponents.client.components.tree.AbstractTreeCell;
import com.dncomponents.client.components.tree.TreeCellParent;
import com.dncomponents.client.components.tree.TreeMultiSelectionModel;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxParentView;

/**
 * Created by nikolasavic
 */
public class TreeCellCheckboxParent<T, M> extends TreeCellParent<T, M> {

    public TreeCellCheckboxParent(TreeCellCheckboxParentView cellView) {
        super(cellView);
    }

    public TreeCellCheckboxParent() {
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckBox().addValueChangeHandler(event ->
                getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true));
    }

    @Override
    protected void setSelection() {
        TreeMultiSelectionModel.SelectionState state = getSelectionModel().getNodeState(getModel());
        if (state == TreeMultiSelectionModel.SelectionState.INDETERMINATE) {
            getCellView().setIndeterminate(true);
        } else {
            getCellView().setIndeterminate(false);
            setSelected(state == TreeMultiSelectionModel.SelectionState.CHECKED);
        }
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getParentTreeCellCheckboxView(icon);
    }

    TreeMultiSelectionModel getSelectionModel() {
        return (TreeMultiSelectionModel) getOwner().getSelectionModel();
    }

    @Override
    public TreeCellCheckboxParentView getCellView() {
        return (TreeCellCheckboxParentView) super.getCellView();
    }

}

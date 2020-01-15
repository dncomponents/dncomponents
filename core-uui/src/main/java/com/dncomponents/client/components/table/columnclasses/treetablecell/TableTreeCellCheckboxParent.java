package com.dncomponents.client.components.table.columnclasses.treetablecell;

import com.dncomponents.client.components.tree.TreeMultiSelectionModel;
import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxParentView;

/**
 * Created by nikolasavic
 */
public class TableTreeCellCheckboxParent<M> extends TableTreeCellParent<M> {

    public TableTreeCellCheckboxParent(TreeCellCheckboxParentView cellView) {
        super(cellView);
    }

    public TableTreeCellCheckboxParent() {
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckBox().addValueChangeHandler(event ->
                getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true));
        getOwner().getSelectionModel().addSelectionHandler(event -> draw());
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
    public void draw() {
        super.draw();
        setSelection();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeUi().getParentTreeCellCheckboxView();
    }

    TreeMultiSelectionModel getSelectionModel() {
        return (TreeMultiSelectionModel) getOwner().getSelectionModel();
    }

    @Override
    public TreeCellCheckboxParentView getCellView() {
        return (TreeCellCheckboxParentView) super.getCellView();
    }
}

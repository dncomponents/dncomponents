package com.dncomponents.client.components.table.columnclasses.treetablecell;

import com.dncomponents.client.views.core.ui.tree.TreeCellCheckboxSimpleView;

/**
 * Created by nikolasavic
 */
public class TableTreeCellCheckboxSimple<M> extends TableTreeCellSimple<M> {

    public TableTreeCellCheckboxSimple() {
    }

    public TableTreeCellCheckboxSimple(TreeCellCheckboxSimpleView treeCellView) {
        super(treeCellView);
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().getCheckBox().addValueChangeHandler(event ->
                getOwner().getSelectionModel().setSelected(getModel(), event.getValue(), true)
        );
        getOwner().getSelectionModel().addSelectionHandler(event -> draw());
    }

    @Override
    public void draw() {
        super.draw();
        setSelectionBase();
    }

    @Override
    public TreeCellCheckboxSimpleView getCellView() {
        return (TreeCellCheckboxSimpleView) super.getCellView();
    }

    @Override
    protected void initViewFromOwner() {
        cellView = getUi().getTreeUi().getTreeCellCheckBoxView(null);
    }
}

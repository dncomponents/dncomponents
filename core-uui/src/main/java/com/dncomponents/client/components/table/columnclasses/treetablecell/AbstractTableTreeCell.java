package com.dncomponents.client.components.table.columnclasses.treetablecell;


import com.dncomponents.client.components.TableTree;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.ui.tabletree.TableTreeUi;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;

/**
 * Created by nikolasavic
 */
public abstract class AbstractTableTreeCell<M> extends TableCell<TreeNode<M>, Object> {

    public AbstractTableTreeCell() {
    }

    public AbstractTableTreeCell(BaseTreeCellView cellView) {
        super(cellView);
    }

    public static AbstractTableTreeCell getCell(TreeNode treeNode, boolean checkBox) {
        if (checkBox)
            return getCellCheckBox(treeNode);
        else
            return getCell(treeNode);
    }

    private static AbstractTableTreeCell getCellCheckBox(TreeNode treeNode) {
        if (treeNode.isLeaf()) {
            return new TableTreeCellCheckboxSimple();
        } else {
            return new TableTreeCellCheckboxParent();
        }
    }

    public static AbstractTableTreeCell getCell(TreeNode treeNode) {
        if (treeNode.isLeaf()) {
            return new TableTreeCellSimple();
        } else {
            return new TableTreeCellParent();
        }
    }

    @Override
    public void draw() {
        super.draw();
        getCellView().setPadding(getModel().getLevel() * 10 + 5);
    }

    @Override
    protected TableTreeUi getUi() {
        return (TableTreeUi) super.getUi();
    }

    @Override
    public TableTree<M> getOwner() {
        return (TableTree<M>) super.getOwner();
    }

    @Override
    public BaseTreeCellView getCellView() {
        return (BaseTreeCellView) super.getCellView();
    }

    public void setVisible(boolean b) { //TODO
        cellView.asElement().setAttribute("style", "display: none;");
    }
}

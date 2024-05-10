/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.components.table.columnclasses.treetablecell;


import com.dncomponents.client.components.TableTree;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.ui.tabletree.TableTreeUi;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;


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
        getCellView().setShift(getModel().getLevel() * 10 + 5);
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

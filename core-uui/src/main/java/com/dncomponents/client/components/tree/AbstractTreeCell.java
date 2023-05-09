/*
 * Copyright 2023 dncomponents
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

package com.dncomponents.client.components.tree;

import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.Tree;
import com.dncomponents.client.components.tree.checkbox.TreeCellCheckboxParent;
import com.dncomponents.client.components.tree.checkbox.TreeCellCheckboxSimple;
import com.dncomponents.client.views.core.ui.tree.BaseTreeCellView;
import com.dncomponents.client.views.core.ui.tree.HasTreeUi;
import com.dncomponents.client.views.core.ui.tree.TreeUi;
import com.dncomponents.client.views.core.ui.tree.TriConsumer;
import elemental2.dom.HTMLElement;
import jsinterop.base.Js;

/**
 * Created by nikolasavic
 */
public abstract class AbstractTreeCell<T, M> extends BaseCell<TreeNode<T>, M> {

    protected String icon;

    public AbstractTreeCell() {
    }

    public AbstractTreeCell(BaseTreeCellView cellView) {
        super(cellView);
    }

    public static <T, M> AbstractTreeCell<T, M> getCell(TreeNode<T> treeNode, boolean checkBox) {
        AbstractTreeCell<T, M> treeCell;
        if (checkBox)
            treeCell = getCellCheckBox(treeNode);
        else
            treeCell = getCell(treeNode);
        if (treeNode.getUserObject() instanceof HasIcon)
            treeCell.icon = ((HasIcon) treeNode.getUserObject()).getIcon();
        return treeCell;
    }

    private static <T, M> AbstractTreeCell<T, M> getCellCheckBox(TreeNode treeNode) {
        if (treeNode.isLeaf()) {
            return new TreeCellCheckboxSimple<>();
        } else {
            return new TreeCellCheckboxParent<>();
        }
    }

    public static <T, M> AbstractTreeCell<T, M> getCell(TreeNode treeNode) {
        if (treeNode.isLeaf()) {
            return new TreeCellSimple();
        } else {
            return new TreeCellParent();
        }
    }

    @Override
    protected void bind() {
        super.bind();
        getCellView().setDraggable(isDragAndDropEnabled());
        if (isDragAndDropEnabled()) {
            getCellView().onItemDragged(new TriConsumer<HTMLElement, HTMLElement, Boolean>() {
                @Override
                public void accept(HTMLElement from, HTMLElement to, Boolean inserted) {
                    final TreeNode root = getOwner().getRoot();
                    final BaseCell cell = getOwner().getCell(from);
                    final BaseCell cellTo = getOwner().getCell(to);
                    final TreeNode fromModel = Js.cast(cell.getModel());
                    final TreeNode toModel = Js.cast(cellTo.getModel());
                    if (toModel.isParentOf(fromModel) || toModel == fromModel) {
                        return;
                    }
                    fromModel.removeFromParent();
                    if (inserted) {
                        toModel.insertAfter(fromModel);
                    } else {
                        toModel.add(fromModel);
                    }
                    getOwner().setRoot(root);
                    getOwner().drawData();
                }
            });

        }
    }

    @Override
    public void draw() {
        super.draw();
        getCellView().setPadding(getModel().getLevel() * 10 + 5);
    }

    @Override
    public Tree getOwner() {
        return (Tree) super.getOwner();
    }

    @Override
    protected TreeUi getUi() {
        return ((HasTreeUi) super.getUi()).getTreeUi();
    }

    @Override
    public BaseTreeCellView getCellView() {
        return (BaseTreeCellView) super.getCellView();
    }

    public void setVisible(boolean b) {
        cellView.asElement().setAttribute("style", b ? "display:block" : "display: none;");
    }
}

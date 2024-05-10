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

package com.dncomponents.client.components.multi;

import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.dropdown.DropDownTreeNodePanelView;
import elemental2.dom.DomGlobal;

import java.util.List;
import java.util.stream.Collectors;


public class DropDownTreeNodePanel<T> extends BaseHasView<TreeNode<T>, DropDownTreeNodePanelView> {

    DropDownMultiLevel<T> dropDown;

    DropDownItemMultiLevel<T> dropDownItem;

    DropDownTreeNodePanel<T> nextDropDownPanel;
    DropDownTreeNodePanel<T> previousDropDownPanel;


    private List<DropDownItemMultiLevel<T>> items;

    boolean mouseOver;
    boolean visible;

    public DropDownTreeNodePanel(DropDownMultiLevel<T> dropDown, TreeNode<T> node) {
        super(dropDown.getView().getDropDownTreeNodePanelView());
        this.dropDown = dropDown;
        setValue(node);
        init();
    }

    public DropDownTreeNodePanel(DropDownItemMultiLevel<T> dropDownItem, TreeNode<T> node) {
        super(dropDownItem.owner.dropDown.getView().getDropDownTreeNodePanelView());
        this.dropDownItem = dropDownItem;
        this.dropDown = dropDownItem.owner.dropDown;
        setValue(node);
        init();
    }

    public DropDownTreeNodePanel(DropDownTreeNodePanelView view) {
        super(view);
        init();
    }

    private void init() {
        items = value.getChildren()
                .stream()
                .map(tTreeNode -> createItem(tTreeNode))
                .collect(Collectors.toList());
        items.forEach(e -> view.add(e));
        view.addMouseEnterHandler(e -> mouseOver = true);
        view.addMouseLeaveHandler(e -> {
            mouseOver = false;
            DomGlobal.setTimeout(p -> {
                if (!((nextDropDownPanel != null && nextDropDownPanel.mouseOver)
                      || isDropDownItemMouseOver()
                      || isFirstLevel())) {
                    DropDownTreeNodePanel<T> pnl = DropDownTreeNodePanel.this;
                    pnl.show(false);
                    while (pnl != null
                           && pnl.previousDropDownPanel != null
                           && !pnl.previousDropDownPanel.mouseOver
                           && pnl.previousDropDownPanel.visible
                           && pnl.previousDropDownPanel.getValue().getLevel() >= 0) {
                        pnl.show(false);
                        pnl = pnl.previousDropDownPanel;
                    }
                }
            }, 50);
        });
    }

    private boolean isFirstLevel() {
        return getValue().getLevel() == 0;
    }

    private DropDownItemMultiLevel<T> createItem(TreeNode<T> item) {
        if (item.isLeaf())
            return new DropDownItemMultiLevel(this, item);
        else
            return new DropDownItemMultiLevelParent(this, item);
    }

    public void show(boolean b) {
        show(dropDownItem, b, "right-start");
    }

    void show(IsElement relativeElement, boolean b, String orientation) {
        view.show(relativeElement, b, orientation);
        visible = b;
        if (!b && nextDropDownPanel != null && nextDropDownPanel.visible)
            nextDropDownPanel.show(false);
    }

    private boolean isDropDownItemMouseOver() {
        if (dropDownItem instanceof DropDownItemMultiLevelParent) {
            return ((DropDownItemMultiLevelParent<T>) dropDownItem).mouseOver;
        } else
            return false;
    }
}

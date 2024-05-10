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
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemMultiLevelParentView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import elemental2.dom.DomGlobal;
import elemental2.dom.MouseEvent;


public class DropDownItemMultiLevelParent<T> extends DropDownItemMultiLevel<T> {

    boolean menuVisible;

    DropDownTreeNodePanel<T> nextDropDownPanel;

    boolean mouseOver;


    public DropDownItemMultiLevelParent(DropDownTreeNodePanel<T> panel, TreeNode<T> node) {
        super(panel.dropDown.getView().getDropDownItemMultiLevelParentView(), panel, node);
        initT();
    }

    public DropDownItemMultiLevelParent(DropDownItemView view) {
        super(view);
    }


    private void initT() {
        getView().addClickHandler(this::onClick);
        getView().addMouseLeaveHandler(this::onMouseLeave);
        getView().addMouseEnterHandler(this::onMouseEnter);
    }

    private void showMenuItems(boolean b) {
        if (b) {
            if (nextDropDownPanel == null) {
                nextDropDownPanel = new DropDownTreeNodePanel<>(this, value);
                nextDropDownPanel.dropDown.getView().getRootView().addDropDownPanel(nextDropDownPanel);
            }
            owner.nextDropDownPanel = nextDropDownPanel;
            nextDropDownPanel.previousDropDownPanel = owner;
            nextDropDownPanel.show(b);
        } else {
            nextDropDownPanel.asElement().remove();
            nextDropDownPanel = null;
        }
    }


    private void onMouseLeave(MouseEvent event) {
        if (owner.dropDown.getTriggerType().equals(DropDownMultiLevel.TriggerType.HOVER)) {
            mouseOver = false;
            DomGlobal.setTimeout(e -> {
                if (nextDropDownPanel != null && !nextDropDownPanel.mouseOver)
                    showMenuItems(false);
            }, 50);
        }
    }

    private void onMouseEnter(MouseEvent mouseEvent) {
        mouseOver = true;
        if (owner.dropDown.getTriggerType().equals(DropDownMultiLevel.TriggerType.HOVER)) {
            showMenuItems(true);
        }
    }

    private void onClick(MouseEvent mouseEvent) {
        if (owner.dropDown.getTriggerType().equals(DropDownMultiLevel.TriggerType.CLICK)) {
            showMenuItems(menuVisible = !menuVisible);
        }
    }

    @Override
    protected DropDownItemMultiLevelParentView getView() {
        return (DropDownItemMultiLevelParentView) super.getView();
    }

}

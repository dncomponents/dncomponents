package com.dncomponents.client.components.multi;

import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemMultiLevelParentView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import com.google.gwt.user.client.Timer;
import elemental2.dom.MouseEvent;

/**
 * @author nikolasavic
 */
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
            Timer timer = new Timer() {
                @Override
                public void run() {
                    if (nextDropDownPanel!=null && !nextDropDownPanel.mouseOver)
                        showMenuItems(false);
                }
            };
            timer.schedule(50);
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
package com.dncomponents.client.components.multi;

import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.ViewSlots;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public class DropDownItemMultiLevel<T> extends BaseHasView<TreeNode<T>, DropDownItemView> {

    DropDownTreeNodePanel<T> owner;
    private boolean selected;


    protected DropDownItemMultiLevel(DropDownItemView view, DropDownTreeNodePanel<T> owner, TreeNode<T> node) {
        super(view);
        init(owner, node);
    }

    public DropDownItemMultiLevel(DropDownTreeNodePanel<T> owner, TreeNode<T> node) {
        super(owner.dropDown.getView().getDropDownItemView());
        init(owner, node);
    }

    private void init(DropDownTreeNodePanel<T> panel, TreeNode<T> node) {
        this.owner = panel;
        setRenderer(panel.dropDown.itemRenderer);
        setValue(node);
        view.addClickHandler(mouseEvent ->
                owner.dropDown.setSelected(DropDownItemMultiLevel.this,
                        !DropDownItemMultiLevel.this.selected, true));
    }

    public DropDownItemMultiLevel(DropDownItemView view) {
        super(view);
    }

    public void setContent(String content) {
        view.setContent(content);
    }

    public void setContent(HTMLElement content) {
        view.setHtmlContent(content);
    }

    @Deprecated
    public boolean isActive() {
        return selected;
    }

    @Deprecated
    public void setActive(boolean active) {
        this.selected = active;
        view.setActive(active);
    }


    public void setRenderer(MainRenderer<TreeNode<T>> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    protected ViewSlots getViewSlots() {
        return super.getViewSlots();
    }
}
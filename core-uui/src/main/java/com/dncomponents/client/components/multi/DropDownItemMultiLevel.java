package com.dncomponents.client.components.multi;

import com.dncomponents.client.components.tree.TreeNode;
import com.dncomponents.client.views.ViewSlots;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemViewSlots;
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
        setRenderer(panel.dropDown.renderer);
        setValue(node);
        init();
    }

    public DropDownItemMultiLevel(DropDownItemView view) {
        super(view);
    }

    void init() {
        view.addClickHandler(mouseEvent ->
                owner.dropDown.setSelected(DropDownItemMultiLevel.this,
                        !DropDownItemMultiLevel.this.selected, true));
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

    public interface DropDownItemRenderer<T> extends Renderer<T, DropDownItemViewSlots> {

    }

    public void setRenderer(DropDownItemRenderer<TreeNode<T>> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    protected ViewSlots getViewSlots() {
        return super.getViewSlots();
    }
}
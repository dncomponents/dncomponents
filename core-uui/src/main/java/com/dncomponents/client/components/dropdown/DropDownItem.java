package com.dncomponents.client.components.dropdown;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.CanSelect;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.core.ui.dropdown.DropDownItemView;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public class DropDownItem<T> extends BaseComponent<T, DropDownItemView> implements CanSelect {

    DropDown<T> dropDown;
    private boolean selected;

    public DropDownItem(DropDown<T> dropDown, T userObject) {
        super(dropDown.getView().getDropDownItemView());
        this.dropDown = dropDown;
        setRenderer(dropDown.renderer);
        setUserObject(userObject);
        init();
    }


    public DropDownItem(DropDownItemView view) {
        super(view);
    }

    private void init() {
        view.addClickHandler(mouseEvent ->
                dropDown.setSelected(DropDownItem.this, !DropDownItem.this.isSelected(), true));
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

    public static String is() {
        return "dropdown-item-dn";
    }

    @Override
    public void setSelected(boolean b) {
        this.selected = b;
        view.setActive(selected);
    }

    @Override
    public boolean isSelected() {
        return selected;
    }


    public void setRenderer(MainRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }
}

package com.dncomponents.material.client.tabletree;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import com.dncomponents.material.client.table.cell.TableCellViewImpl;
import elemental2.dom.HTMLElement;

public class TableTreeCellViewImpl extends TableCellViewImpl implements ParentTreeCellView {

    @UiField
    HTMLElement openCloseElement;
    @UiField
    String openStyle;
    @UiField
    String closeStyle;
    @UiField
    String modelSelected;

    HtmlBinder binder = HtmlBinder.create(TableTreeCellViewImpl.class, this);
    @UiField
    public HTMLElement valuePanel;

    public TableTreeCellViewImpl() {
        binder.bind();
    }

    public TableTreeCellViewImpl(String template) {
        super(template);
    }


    @Override
    public HTMLElement getValuePanel() {
        return valuePanel;
    }

    @Override
    public void setOpened(boolean b) {
        if (!b)
            openCloseElement.innerHTML = openStyle;
        else
            openCloseElement.innerHTML = closeStyle;

    }

    @Override
    public void addOpenCloseHandler(BaseEventListener handler) {
        handler.addTo(openCloseElement);
    }

    @Override
    public void setActive(boolean b) {

    }

    @Override
    public void setShift(int level) {

    }
}

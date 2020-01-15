package com.dncomponents.bootstrap.client.tabletree;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.bootstrap.client.table.cell.TableCellViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.dom.handlers.BaseEventListener;
import com.dncomponents.client.views.core.ui.tree.ParentTreeCellView;
import elemental2.dom.HTMLElement;

/**
 * @author nikolasavic
 */
public class TableTreeCellViewImpl extends TableCellViewImpl implements ParentTreeCellView {

    @UiField
    HTMLElement openCloseElement;
    @UiStyle
    String openStyle;
    @UiStyle
    String closeStyle;
    @UiStyle
    String modelSelected;

    HtmlBinder binder = HtmlBinder.get(TableTreeCellViewImpl.class, this);
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
            openCloseElement.className = openStyle;
        else
            openCloseElement.className = closeStyle;

    }

    @Override
    public void addOpenCloseHandler(BaseEventListener handler) {
        handler.addTo(openCloseElement);
    }

    @Override
    public void setActive(boolean b) {

    }

    @Override
    public void setPadding(double padding) {

    }
}

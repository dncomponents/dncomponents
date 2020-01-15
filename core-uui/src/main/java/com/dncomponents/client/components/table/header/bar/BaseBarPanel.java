package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.table.header.HeaderCellHolder;
import com.dncomponents.client.components.table.header.HeaderWithModifiers;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.pcg.ComponentUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.BaseBarPanelView;

import java.util.List;

/**
 * Base class for showing list of {@link HeaderWithModifiers} items.
 *
 * @author nikolasavic
 */
public abstract class BaseBarPanel<M extends HeaderWithModifiers, T extends BaseBarPanelView<M>>
        extends BaseComponent<Object, ComponentUi<T>> implements BaseBarPanelView.Presenter<M> {
    protected HeaderCellHolder headerCellHolder;

    public BaseBarPanel(ComponentUi ui, HeaderCellHolder cellHolder) {
        super(ui);
        this.headerCellHolder = cellHolder;
        view.getRootView().setPresenter(this);
    }


    private List<ColumnConfig> getColumnsName() {
        return headerCellHolder.getTable().getColumnConfigs();
    }

    @Override
    public void loadColumnsDropDown() {
        view.getRootView().initColumns(getColumnsName());
    }

    public IsElement getLabel() {
        return view.getRootView().getLabel();
    }

    public void setHeaderCellHolder(HeaderCellHolder headerCellHolder) {
        this.headerCellHolder = headerCellHolder;
    }
}
package com.dncomponents.material.client.table.header.bar;

import com.dncomponents.UiField;
import com.dncomponents.client.components.*;
import com.dncomponents.client.components.core.CellContext;
import com.dncomponents.client.components.core.CellFactory;
import com.dncomponents.client.components.list.ListCell;
import com.dncomponents.client.components.table.header.bar.ColumnChooseBarPanelView;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.popover.Popover;
import com.dncomponents.client.components.tooltip.BaseTooltip;
import com.dncomponents.client.components.tooltip.Tooltip;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class ColumnChooseBarPanelViewImpl implements ColumnChooseBarPanelView {

    @UiField
    HTMLElement root;

    Button label = new Button("Hide fields");

    {
        label.asElement().className = ("mdc-button mdc-button--outlined");
    }

    ListData<ColumnConfig, String> list;

    Popover popover;

    HtmlBinder uiBinder = HtmlBinder.get(ColumnChooseBarPanelViewImpl.class, this);


    public ColumnChooseBarPanelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        list = new ListData<>(ColumnConfig::getColumnName);
        list.getRowCellConfig().setCellFactory(new CellFactory<ColumnConfig, String, AbstractCellComponent<ColumnConfig, ?, ?>>() {
            @Override
            public BaseCell<ColumnConfig, String> getCell(CellContext<ColumnConfig, String, AbstractCellComponent<ColumnConfig, ?, ?>> c) {
                return new ListCell<>();
            }
        });

        list.getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        this.asElement().appendChild(list.asElement());
        DomUtil.setWidth(label.asElement(), "180px");
        popover = new Popover(label.asElement(), Tooltip.Orientation.BOTTOM);
        popover.setTrigger(BaseTooltip.Trigger.CLICK);
        popover.setContent(this.asElement());
    }

    @Override
    public IsElement getLabel() {
        return label;
    }

    @Override
    public DefaultMultiSelectionModel getSelectionModel() {
        return list.getSelectionModel();
    }

    @Override
    public HasRows<ColumnConfig> getHasRows() {
        return list;
    }

    @Override
    public HasOpenHandlers<Popover> getPopupShowHandler() {
        return popover;
    }

    @Override
    public void update(int size) {
        if (size > 0)
            label.setText("Hidden " + BaseBarPanelViewImpl.getChip(size + "") + (size > 1 ? "fields" : "field") + "\n");
        else
            label.setText("Hide fields");
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}

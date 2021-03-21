package com.dncomponents.bootstrap.client.table.header.bar;

import com.dncomponents.UiField;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.components.popover.Popover;
import com.dncomponents.client.components.table.header.HeaderWithModifiers;
import com.dncomponents.client.components.tooltip.BaseTooltip;
import com.dncomponents.client.components.tooltip.Tooltip;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.BaseBarPanelView;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;

import java.util.List;

/**
 * @author nikolasavic
 */
public abstract class BaseBarPanelViewImpl<T extends HeaderWithModifiers> implements BaseBarPanelView<T> {

    @UiField
    HTMLElement root;
    @UiField
    HTMLElement contentPanel;
    @UiField
    HTMLElement dropDownHolder;

    protected static String defaultBtnStyle = "btn btn-outline-secondary me-3 mb-3";
    protected Autocomplete<ColumnConfig> autocomplete = new Autocomplete<>(ColumnConfig::getName);

    protected Presenter<T> presenter;

    protected Button label = new Button();
    private String dropDownTitle;

    protected void bind() {
        DomUtil.setWidth(label.asElement(), "180px");
        Popover popover = new Popover(label.asElement(), Tooltip.Orientation.BOTTOM);
        popover.setTrigger(BaseTooltip.Trigger.CLICK);
        popover.setContent(this.asElement());
        DomUtil.setWidth(popover.asElement(), "630px");
        DomUtil.setMaxWidth(popover.asElement(), "630px");
        DomUtil.replace(autocomplete.asElement(), dropDownHolder);
        autocomplete.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                presenter.loadColumnsDropDown();
            }
        });
        autocomplete.addValueChangeHandler(new ValueChangeHandler<ColumnConfig>() {
            @Override
            public void onValueChange(ValueChangeEvent<ColumnConfig> event) {
                presenter.addItemToBarPanel(event.getValue());
                autocomplete.setName(dropDownTitle);
                autocomplete.setValue(null);
            }
        });
    }

    @Override
    public void initColumns(List<ColumnConfig> columns) {
        autocomplete.setRowsData(columns);
    }

    @Override
    public void setPresenter(Presenter<T> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void clear() {
        contentPanel.innerHTML = "";
    }

    @Override
    public void setColor(String barColor) {
        DomUtil.setBackgroundColor(asElement(), barColor);
    }

    @Override
    public void setDropDownTitle(String dropDownTitle) {
        autocomplete.setName(dropDownTitle);
        this.dropDownTitle = dropDownTitle;
    }

    @Override
    public void addBarItem(IsElement barItem) {
        contentPanel.appendChild(barItem.asElement());
    }

    @Override
    public IsElement getLabel() {
        return label;
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}

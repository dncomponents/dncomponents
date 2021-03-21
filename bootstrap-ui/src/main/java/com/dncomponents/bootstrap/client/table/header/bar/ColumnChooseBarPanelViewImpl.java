package com.dncomponents.bootstrap.client.table.header.bar;

import com.dncomponents.UiField;
import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.HasRowsDataList;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.open.HasOpenHandlers;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.list.ListCellCheckbox;
import com.dncomponents.client.components.popover.Popover;
import com.dncomponents.client.components.table.header.bar.ColumnChooseBarPanelView;
import com.dncomponents.client.components.tooltip.BaseTooltip;
import com.dncomponents.client.components.tooltip.Tooltip;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import static com.dncomponents.bootstrap.client.table.header.bar.BaseBarPanelViewImpl.defaultBtnStyle;

/**
 * @author nikolasavic
 */
public class ColumnChooseBarPanelViewImpl implements ColumnChooseBarPanelView {

    @UiField
    HTMLElement root;

    Button label = new Button("Hide fields");

    {
        DomUtil.setStyle(label, defaultBtnStyle);
    }

    ListData<ColumnConfig, String> list;

    Popover popover;

    HtmlBinder uiBinder = HtmlBinder.get(ColumnChooseBarPanelViewImpl.class, this);


    public ColumnChooseBarPanelViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        list = new ListData<>(columnConfig -> columnConfig.getName());
        list.getRowCellConfig().setCellFactory(c -> new ListCellCheckbox<>());
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
    public HasRowsDataList<ColumnConfig> getHasRows() {
        return list;
    }

    @Override
    public HasOpenHandlers<Popover> getPopupShowHandler() {
        return popover;
    }

    @Override
    public void update(int size) {
        if (size > 0) {
            label.setText("Hidden <span class=\"badge badge-light\">" + size + "</span>" + (size > 1 ? " fields" : " field") + "\n");
            DomUtil.setStyle(label, "btn btn-primary me-3 mb-3");
        } else {
            label.setText("Hide fields");
            DomUtil.setStyle(label, defaultBtnStyle);
        }
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }
}

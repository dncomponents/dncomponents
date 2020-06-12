package com.dncomponents.client.components.table.header.bar;

import com.dncomponents.client.components.ColumnConfig;
import com.dncomponents.client.components.core.events.open.OpenEvent;
import com.dncomponents.client.components.core.events.open.OpenHandler;
import com.dncomponents.client.components.core.events.selection.SelectionEvent;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.components.Table;
import com.dncomponents.client.components.core.BaseComponent;

import java.util.List;

/**
 * @author nikolasavic
 */
public class ColumnChooseBarPanel extends BaseComponent<Object, ColumnChooseBarPanelView> {

    Table<Object> table;

    public ColumnChooseBarPanel(Table table, ColumnChooseBarPanelView view) {
        super(view);
        this.table = table;
        bind();
    }

    private void bind() {
         view.getSelectionModel().addSelectionHandler(new SelectionHandler<List>() {
            @Override
            public void onSelection(SelectionEvent<List> event) {
                table.getColumnConfigs()
                        .forEach(c -> c.setVisible(event.getSelectedItem().contains(c)));
                table.drawHeader();
                table.drawData();
                view.update(table.getColumnConfigs().size() - event.getSelectedItem().size());

            }
        });
        view.getPopupShowHandler().addOpenHandler(new OpenHandler() {
            @Override
            public void onOpen(OpenEvent event) {
                view.getHasRows().setRowsData(table.getColumnConfigs());
                for (ColumnConfig c : table.getColumnConfigs()) {
                    view.getSelectionModel().setSelected(c, c.isVisible(), false);
                }
                view.getHasRows().drawData();
            }
        });
    }


    public IsElement getLabel() {
        return view.getLabel();
    }
}
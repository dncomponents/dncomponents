package com.dncomponents.bootstrap.client.table.header.bar;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.table.header.bar.baritem.TableBarViewImpl;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.table.header.bar.ColumnChooseBarPanelView;
import com.dncomponents.client.views.core.ui.table.headers.bar.TableBarUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.TableBarView;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.FilterBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.GroupByBarPanelUi;
import com.dncomponents.client.views.core.ui.table.headers.bar.panel.SortBarPanelUi;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TableBarUiImpl implements TableBarUi {

    @UiField("table-bar-groupby-ui")
    HTMLTemplateElement tableBarGroupByUi;
    @UiField("table-bar")
    HTMLTemplateElement tableBar;
    @UiField
    HTMLTemplateElement columnChooseBar;

    TableBarView tableBarView;

    HtmlBinder uiBinder = HtmlBinder.get(TableBarUiImpl.class, this);

    public TableBarUiImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    public TableBarUiImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public GroupByBarPanelUi getGroupByBarPanelUi() {
        return new GroupByBarPanelUiImpl(tableBarGroupByUi);
    }

    @Override
    public SortBarPanelUi getSortBarPanelUi() {
        return new SortBarPanelUiImpl(tableBarGroupByUi);
    }

    @Override
    public FilterBarPanelUi getFilterBarPanelUi() {
        return new FilterBarPanelUiImpl(tableBarGroupByUi);
    }

    @Override
    public TableBarView getRootView() {
        if (tableBarView == null) tableBarView = new TableBarViewImpl(tableBar);
        return tableBarView;
    }

    @Override
    public ColumnChooseBarPanelView getColumnChooseBarPanelView() {
        return new ColumnChooseBarPanelViewImpl(columnChooseBar);
    }
}

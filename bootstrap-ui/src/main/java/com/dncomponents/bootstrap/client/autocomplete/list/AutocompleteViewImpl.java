package com.dncomponents.bootstrap.client.autocomplete.list;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.autocomplete.BaseAutocompleteViewImpl;
import com.dncomponents.client.components.HasRows;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.HTMLTemplateElement;

import java.util.List;
import java.util.function.Function;

/**
 * @author nikolasavic
 */

@UiTemplate
public class AutocompleteViewImpl<T> extends BaseAutocompleteViewImpl<T> implements AutocompleteView<T> {

    protected static final String VIEW_ID = "DEFAULT";
    @UiField
    ListData<T, String> list;

    HtmlBinder uiBinder = HtmlBinder.get(AutocompleteViewImpl.class, this);

    public AutocompleteViewImpl() {
        uiBinder.bind();
        init();
    }

    public AutocompleteViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public AutocompleteViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    private void init() {
        list.setEditable(false);
        list.getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.SINGLE);
    }

    @Override
    public void focusList() {
        if (!list.getCells().isEmpty())
            list.getSelectionModel().focusCell(list.getRowCell(0));
    }

    @Override
    public DefaultMultiSelectionModel getSelectionModel() {
        return list.getSelectionModel();
    }

    @Override
    public void setData(List<T> data) {
        list.setRowsData(data);
        list.drawData();
    }

    @Override
    public List<T> getRowsData() {
        return list.getRowsData();
    }

    @Override
    public void setFilter(Filter<T> filter) {
        list.addFilter(filter);
    }

    @Override
    public void setCellConfig(CellConfig cellConfig) {
//        list.setCellConfig(cellConfig);
    }

    @Override
    public HasRows<T> getHasRowsData() {
        return list;
    }

    @Override
    public void setFieldGetter(Function<T, String> fieldGetter) {
        list.getRowCellConfig().setFieldGetter(fieldGetter);
    }


    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<T>> handler) {
        return list.getSelectionModel().addSelectionHandler(handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        list.fireEvent(event);
    }
}
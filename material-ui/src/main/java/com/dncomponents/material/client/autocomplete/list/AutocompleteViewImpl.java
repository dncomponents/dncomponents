package com.dncomponents.material.client.autocomplete.list;

import com.dncomponents.UiField;
import com.dncomponents.Component;
import com.dncomponents.client.components.HasRowsDataList;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.components.core.events.selection.SelectionHandler;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import com.dncomponents.material.client.autocomplete.BaseAutocompleteViewImpl;
import elemental2.dom.HTMLTemplateElement;

import java.util.List;
import java.util.function.Function;

/**
 * @author nikolasavic
 */

@Component
public class AutocompleteViewImpl<T> extends BaseAutocompleteViewImpl<T> implements AutocompleteView<T> {

    protected static final String VIEW_ID = "DEFAULT";


    @UiField
    ListData<T, String> list;

    HtmlBinder uiBinder = HtmlBinder.create(AutocompleteViewImpl.class, this);

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
        list.getRowCellConfig().setFieldGetter(t -> t + "");
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
    public void setFilter(Filter<T> filter) {
        list.addFilter(filter);
    }

    @Override
    public HasRowsDataList<T> getHasRowsData() {
        return list;
    }

    @Override
    public void setFieldGetter(Function<T, String> fieldGetter) {
        list.getRowCellConfig().setFieldGetter(fieldGetter);
    }

    @Override
    public CellConfig<T, String> getRowCellConfig() {
        return list.getRowCellConfig();
    }


    @Override
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<T>> handler) {
        return list.getSelectionModel().addSelectionHandler(handler);
    }

}

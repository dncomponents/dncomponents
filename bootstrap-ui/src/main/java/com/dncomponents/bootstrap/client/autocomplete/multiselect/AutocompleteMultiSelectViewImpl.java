package com.dncomponents.bootstrap.client.autocomplete.multiselect;

import com.dncomponents.UiField;
import com.dncomponents.UiTemplate;
import com.dncomponents.bootstrap.client.autocomplete.BaseAutocompleteViewImpl;
import com.dncomponents.client.components.HasRows;
import com.dncomponents.client.components.ListData;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.OnFocusHandler;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.HTMLUListElement;
import elemental2.dom.MouseEvent;

import java.util.List;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
@UiTemplate
public class AutocompleteMultiSelectViewImpl<T> extends BaseAutocompleteViewImpl<T> implements AutocompleteMultiSelectView<T> {

    @UiField
    HTMLElement root;
    @UiField
    HTMLUListElement selectionPanel;
    @UiField
    TextBox textBox;
    @UiField(provided = true)
    ListData<T, String> list = new ListData<>();

    HtmlBinder uiBinder = HtmlBinder.get(AutocompleteMultiSelectViewImpl.class, this);

    public AutocompleteMultiSelectViewImpl() {
        uiBinder.bind();
        init();
    }

    public AutocompleteMultiSelectViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
        init();
    }

    public AutocompleteMultiSelectViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        init();
    }

    @Override
    public void addButtonClickHandler(ClickHandler clickHandler) {
        clickHandler.addTo(root);
    }

    @Override
    public void setTextBoxCurrentValue(String value) {

    }

    private void init() {
        list.getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        list.getSelectionModel().useMetaKeyForSelection(false);
        new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                textBox.setFocus(true);
            }
        }.addTo(root);
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
    public HasRows<T> getHasRowsData() {
        return list;
    }

    @Override
    public void setFieldGetter(Function<T, String> fieldGetter) {
        list.getRowCellConfig().setFieldGetter(fieldGetter);
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
    public void clearItems() {
        selectionPanel.innerHTML = "";
    }

    @Override
    public void addItem(IsElement item) {
        selectionPanel.appendChild(item.asElement());
    }

    @Override
    public void setSearchPanel() {
    }

    @Override
    public void addFocusTextBoxHandler(OnFocusHandler focusHandler) {
        textBox.addFocusHandler(focusHandler);
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
    public HandlerRegistration addSelectionHandler(SelectionHandler<List<T>> handler) {
        return list.getSelectionModel().addSelectionHandler(handler);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        list.fireEvent(event);//TODO check this method
    }
}
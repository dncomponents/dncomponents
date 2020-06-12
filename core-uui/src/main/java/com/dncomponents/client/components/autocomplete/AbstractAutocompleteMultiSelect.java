package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.core.events.value.ValueChangeEvent;
import com.dncomponents.client.components.core.events.value.ValueChangeHandler;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainRendererImpl;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by nikolasavic
 */
public abstract class AbstractAutocompleteMultiSelect<T, C> extends AbstractAutocomplete<T, AutocompleteMultiSelectView<T>, C> {

    protected MainRenderer<T> itemRenderer = new MainRendererImpl<>();

    protected List<AutocompleteMultiSelectItem<T>> items = new ArrayList<>();

    public void setItemRenderer(MainRenderer<T> itemRenderer) {
        this.itemRenderer = itemRenderer;
    }

    void remove(AutocompleteMultiSelectItem item) {
        view.getSelectionModel().setSelected((T) item.getUserObject(), false, true);
    }

    public AbstractAutocompleteMultiSelect(AutocompleteMultiSelectView<T> view) {
        super(view);
        bind();
    }

    public AbstractAutocompleteMultiSelect(AutocompleteMultiSelectView<T> view, Function<T, String> fieldGetter) {
        super(view, fieldGetter);
        bind();
    }

    private void bind() {
        addValueChangeHandler(event -> showList(false));
        view.getSelectionModel().getHasValue().addValueChangeHandler(new ValueChangeHandler<List<T>>() {
            @Override
            public void onValueChange(ValueChangeEvent<List<T>> event) {
                changed(event.getValue());
                showList(true);
            }
        });
    }

    @Override
    public void setValue(C value, boolean fireEvents) {
        super.setValue(value, fireEvents);
        if (value == null)
            changed(null);
    }

    protected void changed(List<T> value) {
        view.clearItems();
        if (value != null) {
            for (T t : value) {
                AutocompleteMultiSelectItem<T> item = new AutocompleteMultiSelectItem<T>(AbstractAutocompleteMultiSelect.this, t);
                view.addItem(item);
            }
        } else {
            view.clearItems();
        }
    }

    @Override
    protected AutocompleteMultiSelectView<T> getView() {
        return super.getView();
    }

}
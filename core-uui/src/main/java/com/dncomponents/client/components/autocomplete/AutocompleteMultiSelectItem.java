package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectItemView;

public class AutocompleteMultiSelectItem<T> extends BaseComponent<T, AutocompleteMultiSelectItemView> {

    protected AbstractAutocompleteMultiSelect multiSelect;

    public AutocompleteMultiSelectItem(AbstractAutocompleteMultiSelect multiSelect, T value) {
        super(multiSelect.getView().getAutocompleteMultiSelectItemView());
        this.multiSelect = multiSelect;
        setRenderer(multiSelect.itemRenderer);
        this.setUserObject(value);
        init();
    }

    private void init() {
        view.addRemoveClickHandler(mouseEvent -> multiSelect.remove(AutocompleteMultiSelectItem.this));
    }

    public void setRenderer(MainRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

}
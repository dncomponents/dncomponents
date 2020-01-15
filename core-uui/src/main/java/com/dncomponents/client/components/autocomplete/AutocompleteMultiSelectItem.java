package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectItemView;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectItemViewSlots;
import elemental2.dom.MouseEvent;

public class AutocompleteMultiSelectItem<T> extends BaseComponent<T, AutocompleteMultiSelectItemView> {

    AutocompleteMultiSelect multiSelect;

    public AutocompleteMultiSelectItem(AutocompleteMultiSelect multiSelect, T value) {
        super(multiSelect.getView().getAutocompleteMultiSelectItemView());
        this.multiSelect = multiSelect;
        setRenderer(multiSelect.itemRenderer);
        this.setUserObject(value);
        init();
    }

    private void init() {
        view.addRemoveClickHandler(new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                multiSelect.remove(AutocompleteMultiSelectItem.this);
            }
        });
    }


    public interface AutocompleteMultiSelectItemRenderer<T> extends Renderer<T, AutocompleteMultiSelectItemViewSlots> {
    }

    public void setRenderer(AutocompleteMultiSelectItemRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    public AutocompleteMultiSelectItemViewSlots getViewSlots() {
        return (AutocompleteMultiSelectItemViewSlots) super.getViewSlots();
    }

}

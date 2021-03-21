package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.core.DefaultCellEditor;

import java.util.List;

public class AutoCompleteEditor<M> extends DefaultCellEditor<M> {

    public AutoCompleteEditor(List list) {
        this();
        getHasValue().setRowsData(list);
    }

    public AutoCompleteEditor() {
        super(new Autocomplete());
    }

    @Override
    public void startEditing() {
        getHasValue().showList(true);
    }

    @Override
    public Autocomplete getHasValue() {
        return (Autocomplete) super.getHasValue();
    }
}

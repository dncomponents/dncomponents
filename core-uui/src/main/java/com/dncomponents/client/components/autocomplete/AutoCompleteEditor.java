package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.core.DefaultCellEditor;

import java.util.List;

public class AutoCompleteEditor<M> extends DefaultCellEditor<M, Autocomplete> {

    public AutoCompleteEditor(List list) {
        this();
        getHasValue().setRowsData(list);
    }

    public AutoCompleteEditor() {
        super(new Autocomplete());
    }

    @Override
    public void startEditing() {
        //todo at first showing it doesn't load list
        getHasValue().showList(true);
    }

    @Override
    public Autocomplete getHasValue() {
        return (Autocomplete) super.getHasValue();
    }
}

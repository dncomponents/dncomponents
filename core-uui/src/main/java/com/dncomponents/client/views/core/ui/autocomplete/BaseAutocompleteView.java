package com.dncomponents.client.views.core.ui.autocomplete;

import com.dncomponents.client.components.HasRowsData;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.views.FocusComponentView;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;

import java.util.List;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public interface BaseAutocompleteView<M> extends FocusComponentView, HasSelectionHandlers<List<M>> {

    String getTextBoxCurrentValue();

    void setTextBoxCurrentValue(String value);

    void addKeyUpHandler(KeyUpHandler keyUpHandler);

    void addButtonClickHandler(ClickHandler clickHandler);

    void setFilter(Filter<M> filter);

    void setCellConfig(CellConfig cellConfig);

    void setStringValue(String value);

    void showListPanel(boolean b);

    void setTextBoxFocused(boolean b);

    void focusList();

    DefaultMultiSelectionModel<M> getSelectionModel();

    HasRowsData<M> getHasRowsData();

    void setFieldGetter(Function<M, String> fieldGetter);
}
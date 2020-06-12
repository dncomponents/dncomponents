package com.dncomponents.client.views.core.ui.autocomplete;

import com.dncomponents.client.components.HasRowsData;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.selection.HasSelectionHandlers;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.components.core.events.filters.Filter;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.views.FocusComponentView;

import java.util.List;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public interface BaseAutocompleteView<M> extends FocusComponentView, HasSelectionHandlers<List<M>> {

    String getTextBoxCurrentValue();

    void setTextBoxCurrentValue(String value);

    void addKeyUpHandler(KeyUpHandler keyUpHandler);

    HandlerRegistration addButtonClickHandler(ClickHandler clickHandler);

    void setFilter(Filter<M> filter);

    void setStringValue(String value);

    void showListPanel(boolean b, Command done);

    void setTextBoxFocused(boolean b);

    void focusList();

    DefaultMultiSelectionModel<M> getSelectionModel();

    HasRowsData<M> getHasRowsData();

    void setFieldGetter(Function<M, String> fieldGetter);

    CellConfig<M, String> getRowCellConfig();

}
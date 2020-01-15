package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.HasRowsData;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.BaseFocusComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectItemViewSlots;
import com.dncomponents.client.views.core.ui.autocomplete.multiselect.AutocompleteMultiSelectUi;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import elemental2.dom.Element;
import elemental2.dom.FocusEvent;
import elemental2.dom.KeyboardEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.dncomponents.client.components.ListData.ListHtmlParser.getChildren;

/**
 * Created by nikolasavic
 */
public class AutocompleteMultiSelect<T> extends BaseFocusComponent<Object, AutocompleteMultiSelectUi<T>> implements HasValue<List<T>>, HasRowsData<T> {

    Function<T, String> textBoxFieldGetter = t -> t + "";
    boolean listShowing;

    HasValue<List<T>> delegate;

    AutocompleteMultiSelectItem.AutocompleteMultiSelectItemRenderer<T> itemRenderer = new AutocompleteMultiSelectItem.AutocompleteMultiSelectItemRenderer<T>() {
        @Override
        public void render(T t, AutocompleteMultiSelectItemViewSlots slots) {
            slots.getMainSlot().innerHTML = t + "";
        }
    };

    List<AutocompleteMultiSelectItem<T>> items = new ArrayList<>();

    public void setItemRenderer(AutocompleteMultiSelectItem.AutocompleteMultiSelectItemRenderer itemRenderer) {
        this.itemRenderer = itemRenderer;
    }

    void remove(AutocompleteMultiSelectItem item) {
        view.getRootView().getSelectionModel().setSelected((T) item.getUserObject(), false, true);
    }


    private static class DefaultCellConfig<T> extends CellConfig<T, String> {
        public DefaultCellConfig() {
            super(t -> t + "");
        }
    }

    private CellConfig<T, ?> cellConfig;
    protected Filter<T> filter = new Filter<T>() {
        @Override
        public boolean test(T o) {
            if (view.getRootView().getTextBoxCurrentValue() == null)
                return true;
            return textBoxFieldGetter
                    .apply(o)
                    .toLowerCase()
                    .contains(view.getRootView().getTextBoxCurrentValue());
        }
    };

    public AutocompleteMultiSelect() {
        super(Ui.get().getAutocompleteMultiSelectView());
        bind();
    }

    public AutocompleteMultiSelect(AutocompleteMultiSelectUi<T> view) {
        super(view);
        bind();
    }

    public AutocompleteMultiSelect(Function<T, String> textBoxFieldGetter) {
        super(Ui.get().getAutocompleteMultiSelectView());
        this.textBoxFieldGetter = textBoxFieldGetter;
        bind();
    }


//    public AutocompleteMultiSelect(CellConfig<T, String> cellConfig) {
//        this(Ui.create().getAutocompleteMultiSelectView(), cellConfig);
//    }


    public AutocompleteMultiSelect(AutocompleteMultiSelectUi view, CellConfig<T, ?> cellConfig) {
        super(view);
        this.cellConfig = cellConfig;
        bind();
    }


    private void bind() {
        this.delegate = view.getRootView().getSelectionModel().getHasValue();
        view.getRootView().showListPanel(false);
        view.getRootView().getSelectionModel().setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        view.getRootView().getSelectionModel().addSelectionHandler(new SelectionHandler<List<T>>() {
            @Override
            public void onSelection(SelectionEvent<List<T>> event) {
                showList(false);
            }
        });
        view.getRootView().getSelectionModel().getHasValue().addValueChangeHandler(new ValueChangeHandler<List<T>>() {
            @Override
            public void onValueChange(ValueChangeEvent<List<T>> event) {
                onValueChanged(event.getValue());
            }
        });
        view.getRootView().setFilter(filter);
        //        view.setCellConfig(cellConfig);
        view.getRootView().addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyboardEvent event) {
                if (event.code.equals("Escape")) {
                    showList(false);
                } else if ("ArrowDown".equals(event.code)) {
                    view.getRootView().focusList();
                } else
                    filter.fireFilterChange();
            }
        });
        addValueChangeHandler(event -> showList(false));
        view.getRootView().addButtonClickHandler(mouseEvent -> showList(!listShowing));
        addBlurHandler(new OnBlurHandler() {
            @Override
            public void onBlur(FocusEvent focusEvent) {
                showList(false);
            }
        });
    }

    private void onValueChanged(List<T> values) {
        view.getRootView().clearItems();
        if (values != null)
            for (T t : values) {
                AutocompleteMultiSelectItem<T> item = new AutocompleteMultiSelectItem<>(AutocompleteMultiSelect.this, t);
                items.add(item);
                view.getRootView().addItem(item);
            }
        view.getRootView().setSearchPanel();
//        showList(false);
    }


    public void showList(boolean b) {
        view.getRootView().showListPanel(b);
        //wait list to show.
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                view.getRootView().setTextBoxFocused(true);
            }
        });
        listShowing = b;
        if (!listShowing) {
//            view.getRootView().setTextBoxCurrentValue(null);
            view.getRootView().setTextBoxFocused(false);
        }
    }

    public boolean isListShowing() {
        return listShowing;
    }

    //todo remove
    public void setData(List<T> data) {
        view.getRootView().setData(data);
    }

    @Override
    protected AutocompleteMultiSelectUi<T> getView() {
        return super.getView();
    }

    public void setName(String name) {
        view.getRootView().setStringValue(name);
    }

    public List<T> getRowsData() {
        return view.getRootView().getRowsData();
    }

    @Override
    public void addRow(T t) {
        view.getRootView().getHasRowsData().addRow(t);
    }

    @Override
    public void insertRow(T t, int index) {

    }

    @Override
    public void removeRow(T t) {
        view.getRootView().getHasRowsData().removeRow(t);
    }

    @Override
    public void setRowsData(List<T> rows) {
        view.getRootView().setData(rows);
    }

    @Override
    public void drawData() {
        view.getRootView().getHasRowsData().drawData();
    }

    @Override
    public List<T> getValue() {
        return delegate.getValue();
    }

    @Override
    public void setValue(List<T> value) {
        List<T> oldValue = getValue();
        delegate.setValue(value);
        if (oldValue != value)
            onValueChanged(value);
    }

    @Override
    public void setValue(List<T> value, boolean fireEvents) {
        delegate.setValue(value, fireEvents);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<List<T>> handler) {
        return delegate.addValueChangeHandler(handler);
    }

    public static class AutocompleteMultiSelectHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static AutocompleteMultiSelectHtmlParser instance;

        private AutocompleteMultiSelectHtmlParser() {
        }

        public static AutocompleteMultiSelectHtmlParser getInstance() {
            if (instance == null)
                return instance = new AutocompleteMultiSelectHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            AutocompleteMultiSelect autocomplete;
            AutocompleteMultiSelectUi view = getView(AutocompleteMultiSelect.class, htmlElement, elements);
            if (view != null)
                autocomplete = new AutocompleteMultiSelect(view);
            else
                autocomplete = new AutocompleteMultiSelect();
            if (htmlElement.hasChildNodes()) {
                getChildren(autocomplete, htmlElement, this);
            }
            replaceAndCopy(htmlElement, autocomplete);
            return autocomplete;
        }

        @Override
        public String getId() {
            return "dn-autocomplete-multi-select";
        }

        @Override
        public Class getClazz() {
            return AutocompleteMultiSelect.class;
        }

        @Override
        public boolean isPremium() {
            return true;
        }
    }

}
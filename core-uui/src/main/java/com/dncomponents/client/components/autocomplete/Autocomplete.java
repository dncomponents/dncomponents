package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.HasRowsData;
import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import elemental2.dom.Element;
import elemental2.dom.FocusEvent;
import elemental2.dom.KeyboardEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.dncomponents.client.components.ListData.ListHtmlParser.getChildren;

/**
 * @author nikolasavic
 */
public class Autocomplete<T> extends BaseFocusComponent<T, AutocompleteView<T>> implements HasValue<T>, HasRowsData<T> {

    boolean listShowing;
    private T value;


    private static class DefaultCellConfig<T> extends CellConfig<T, String> {
        public DefaultCellConfig() {
            super(t -> t + "");
        }
    }

    Function<T, String> fieldGetter = new Function<T, String>() {
        @Override
        public String apply(T t) {
            return t + "";
        }
    };

    protected Filter<T> filter = new Filter<T>() {
        @Override
        public boolean test(T o) {
            if (view.getTextBoxCurrentValue() == null)
                return true;
            return fieldGetter
                    .apply(o)
                    .toLowerCase()
                    .contains(view.getTextBoxCurrentValue());
        }
    };

    public Autocomplete() {
        super(Ui.get().getAutocompleteView());
        bind();
    }

    public Autocomplete(AutocompleteView<T> view) {
        super(view);
        bind();
    }

    public Autocomplete(Function<T, String> fieldGetter) {
        super(Ui.get().getAutocompleteView());
        this.fieldGetter = fieldGetter;
        bind();
    }


    public Autocomplete(AutocompleteView view, Function<T, String> fieldGetter) {
        super(view);
        this.fieldGetter = fieldGetter;
        bind();
    }

    public void setFieldGetter(Function<T, String> fieldGetter) {
        this.fieldGetter = fieldGetter;
        view.setFieldGetter(fieldGetter);
    }

    private void bind() {
        view.showListPanel(false);
        view.addSelectionHandler(event -> setValue(event.getSelectedItem().stream().findFirst().orElse(null), true));
        view.setFilter(filter);
        view.setFieldGetter(fieldGetter);
//        view.setCellConfig(cellConfig);
        view.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyboardEvent event) {
                if ("ArrowDown".equals(event.code)) {
                    view.focusList();
                } else
                    filter.fireFilterChange();
            }
        });
        addValueChangeHandler(event -> showList(false));
        view.addButtonClickHandler(mouseEvent -> showList(!listShowing));
        addBlurHandler(new OnBlurHandler() {
            @Override
            public void onBlur(FocusEvent focusEvent) {
                showList(false);
            }
        });
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.setValue(value, false);
    }

    public void showList(boolean b) {
        view.showListPanel(b);
        //wait list to show.
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                view.setTextBoxFocused(b);
            }
        });
        listShowing = b;
        if (!listShowing) {
            view.setTextBoxCurrentValue(null);
            filter.fireFilterChange();
        }
    }


    public boolean isListShowing() {
        return listShowing;
    }

    @Override
    public void setValue(T value, boolean fireEvents) {
        T oldValue = getValue();
        this.value = value;
        if (value != null)
            view.setStringValue(fieldGetter.apply(this.value));
        else {
            view.setStringValue(null);
        }
        view.getSelectionModel().getHasValue().setValue(Arrays.asList(value), true);

        if (fireEvents) {
            T newValue = getValue();
            ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
        }
    }

    @Override
    public com.google.gwt.event.shared.HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return ensureHandlers().addHandler(ValueChangeEvent.getType(), handler);
    }

    public void setData(List<T> data) {
        view.setData(data);
    }

    @Override
    protected AutocompleteView<T> getView() {
        return super.getView();
    }

    public void setName(String name) {
        view.setStringValue(name);
    }

    public List<T> getRowsData() {
        return view.getRowsData();
    }

    @Override
    public void addRow(T t) {
        view.getHasRowsData().addRow(t);
    }

    @Override
    public void insertRow(T t, int index) {

    }

    @Override
    public void removeRow(T t) {
        view.getHasRowsData().removeRow(t);
    }

    @Override
    public void setRowsData(List<T> rows) {
        view.getHasRowsData().setRowsData(rows);
        view.getHasRowsData().drawData();
    }

    @Override
    public void drawData() {
        view.getHasRowsData().drawData();
    }

    public static class AutocompleteHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static AutocompleteHtmlParser instance;

        private AutocompleteHtmlParser() {
        }

        public static AutocompleteHtmlParser getInstance() {
            if (instance == null)
                return instance = new AutocompleteHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            Autocomplete autocomplete;
            AutocompleteView view = getView(Autocomplete.class, htmlElement, elements);
            if (view != null)
                autocomplete = new Autocomplete(view);
            else
                autocomplete = new Autocomplete();
            if (htmlElement.hasChildNodes()) {
                getChildren(autocomplete, htmlElement, this);
            }
            replaceAndCopy(htmlElement, autocomplete);
            return autocomplete;
        }

        @Override
        public String getId() {
            return "dn-autocomplete";
        }

        @Override
        public Class getClazz() {
            return Autocomplete.class;
        }

        @Override
        public boolean isPremium() {
            return true;
        }
    }


}
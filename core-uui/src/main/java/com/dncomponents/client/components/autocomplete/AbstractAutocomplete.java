package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.core.BaseFocusComponent;
import com.dncomponents.client.components.core.CellConfig;
import com.dncomponents.client.components.filters.Filter;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.core.ui.autocomplete.BaseAutocompleteView;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import elemental2.dom.FocusEvent;
import elemental2.dom.KeyboardEvent;

/**
 * @author nikolasavic
 */
public abstract class AbstractAutocomplete<T, V extends BaseAutocompleteView<T>> extends BaseFocusComponent<T, V> implements HasValue<T> {

    boolean listShowing;
    private T value;
    protected CellConfig<T, ?> cellConfig;
    protected final Filter<T> filter = new Filter<T>() {
        @Override
        public boolean test(T o) {
            if (view.getTextBoxCurrentValue() == null)
                return true;
            return cellConfig
                    .getFieldGetter()
                    .apply(o)
                    .toString()
                    .toLowerCase()
                    .contains(view.getTextBoxCurrentValue().toLowerCase());
        }
    };

    public AbstractAutocomplete(V view) {
        super(view);
    }

    public AbstractAutocomplete(V view, CellConfig<T, ?> cellConfig) {
        super(view);
        this.cellConfig = cellConfig;
        bind();
    }

    protected void bind() {
        view.showListPanel(false);
        view.addSelectionHandler(event -> setValue(event.getSelectedItem().stream().findFirst().orElse(null), true));
        view.setFilter(filter);
        view.setCellConfig(cellConfig);
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
            view.setStringValue(cellConfig.getFieldGetter().apply(this.value) + "");
        if (fireEvents) {
            T newValue = getValue();
            ValueChangeEvent.fireIfNotEqual(this, oldValue, newValue);
        }
    }

    @Override
    public com.google.gwt.event.shared.HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return ensureHandlers().addHandler(ValueChangeEvent.getType(), handler);
    }

    @Override
    protected V getView() {
        return super.getView();
    }
}
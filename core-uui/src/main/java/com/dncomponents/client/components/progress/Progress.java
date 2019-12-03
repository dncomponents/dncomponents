package com.dncomponents.client.components.progress;


import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.ViewSlots;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import elemental2.dom.Element;

import java.util.*;
import java.util.function.Function;

/**
 * @author nikolasavic
 */
public class Progress extends BaseComponent<Object, ProgressView> implements HasValue<Integer> {
    //percent of progress
    private int percent = 0;
    //turn on/off text on progress bar
    private boolean showPercentText = true;
    //text shown on progress bar
    private Function<Integer, String> percentTextFunction = integer -> integer + "%";

    public Progress(ProgressView view) {
        super(view);
    }

    public Progress(ProgressView view, int percent) {
        super(view);
        setValue(percent);
    }

    public Progress() {
        super(Ui.get().getProgressView());
    }

    public Progress(int percent) {
        this();
        setValue(percent);
    }

    private void draw() {
        view.setBarWidth(percent);
        if (showPercentText)
            view.setBarText(percentTextFunction.apply(percent));
    }

    /**
     * Defines how percent text is displayed on progress bar.
     *
     * @param percentTextFunction function
     */
    public void setPercentText(Function<Integer, String> percentTextFunction) {
        this.percentTextFunction = percentTextFunction;
    }

    /**
     * Toggles display of percent on progress bar. By default it shows only percent text in form e.g 20%
     * To changes content of text being displayed define your own text with {@link #setPercentText(Function)}
     *
     * @param b <code>true</code> to show given text, <code>false</code> to hide it
     */
    public void showPercentText(boolean b) {
        this.showPercentText = b;
        draw();
    }

    /**
     * To ensure that the label text remains legible even for low percentages,
     * consider adding a min-width to the progress bar.
     *
     * @param minimumWidth minimum width in em
     */
    public void setMinimumWidth(int minimumWidth) {
        view.setMinimumWidth(minimumWidth);
    }

    @Override
    public Integer getValue() {
        return percent;
    }

    @Override
    public void setValue(Integer value) {
        setValue(value, false);
    }

    @Override
    public void setValue(Integer value, boolean fireEvents) {
        Integer oldValue = getValue();
        percent = value;
        if (value < 0) percent = 0;
        if (value > 100) percent = 100;
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, percent);
        }
        draw();
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Integer> handler) {
        return ensureHandlers().addHandler(ValueChangeEvent.getType(), handler);
    }

    public static class ProgressHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private Map<String, List<String>> arguments = new HashMap<>();

        {
            arguments.put(VALUE, new ArrayList<>());
        }

        private static final String VALUE = "value";

        private static Progress.ProgressHtmlParser instance;

        private ProgressHtmlParser() {
            arguments.put(VALUE, Collections.emptyList());
        }

        public static Progress.ProgressHtmlParser getInstance() {
            if (instance == null)
                return instance = new Progress.ProgressHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            Progress progress;
            ProgressView view = getView(Progress.class, htmlElement, templateElement);
            if (view != null)
                progress = new Progress(view);
            else
                progress = new Progress();
            if (htmlElement.hasAttribute(VALUE)) {
                progress.setValue(Integer.parseInt(htmlElement.getAttribute(VALUE)));
            }
            replaceAndCopy(htmlElement, progress);
            return progress;
        }

        @Override
        public String getId() {
            return "dn-progress";
        }

        @Override
        public Class getClazz() {
            return Progress.class;
        }

    }

    @Override
    public ViewSlots getViewSlots() {
        return super.getViewSlots();
    }
}

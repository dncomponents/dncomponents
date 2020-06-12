package com.dncomponents.client.components.autocomplete;

import com.dncomponents.client.components.HasRowsDataList;
import com.dncomponents.client.components.ListCellConfig;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import elemental2.dom.Element;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.dncomponents.client.components.ListData.ListHtmlParser.getChildren;

/**
 * @author nikolasavic
 */
public class Autocomplete<T> extends AbstractAutocomplete<T, AutocompleteView<T>, T> {

    public Autocomplete() {
        super(Ui.get().getAutocompleteView());
    }

    public Autocomplete(AutocompleteView<T> view) {
        super(view);
    }

    public Autocomplete(Function<T, String> fieldGetter) {
        super(Ui.get().getAutocompleteView(), fieldGetter);
    }

    public Autocomplete(AutocompleteView view, Function<T, String> fieldGetter) {
        super(view, fieldGetter);
    }

    @Override
    protected AutocompleteView<T> getView() {
        return super.getView();
    }

    public void setName(String name) {
        view.setStringValue(name);
    }

    public void setRowsData(List<T> rows) {
        view.getHasRowsData().setRowsData(rows);
        view.getHasRowsData().drawData();
    }

    public ListCellConfig<T, String> getRowCellConfig() {
        return (ListCellConfig<T, String>) view.getRowCellConfig();
    }

    public HasRowsDataList<T> getHasRowsDataList() {
        return view.getHasRowsData();
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
                getChildren(autocomplete.getView().getHasRowsData(), htmlElement, this);
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

    }

}
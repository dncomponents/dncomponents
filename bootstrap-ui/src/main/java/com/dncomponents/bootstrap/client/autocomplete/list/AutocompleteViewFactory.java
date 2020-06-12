package com.dncomponents.bootstrap.client.autocomplete.list;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.autocomplete.Autocomplete;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.autocomplete.AutocompleteView;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class AutocompleteViewFactory {


    public static class DefaultAutocompleteViewFactory extends AbstractPluginHelper implements ViewFactory<AutocompleteView> {

        private static DefaultAutocompleteViewFactory instance;

        private DefaultAutocompleteViewFactory() {
        }

        public static DefaultAutocompleteViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultAutocompleteViewFactory();
            return instance;
        }

        @Override
        public AutocompleteView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).autocomplete;
            return new AutocompleteViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return AutocompleteViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return AutocompleteViewImpl.class;
        }
    }

}
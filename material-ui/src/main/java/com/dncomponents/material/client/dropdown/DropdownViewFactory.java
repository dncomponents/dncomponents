package com.dncomponents.material.client.dropdown;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.dropdown.DropDownUi;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class DropdownViewFactory {

    public static class DefaultDropdownViewFactory extends AbstractPluginHelper implements ViewFactory<DropDownUi> {

        private static DefaultDropdownViewFactory instance;

        private DefaultDropdownViewFactory() {
        }

        public static DefaultDropdownViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultDropdownViewFactory();
            return instance;
        }

        @Override
        public DropDownUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).dropDownUi;
            return new DropDownUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return DropDownUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return DropDownUiImpl.class;
        }
    }

}
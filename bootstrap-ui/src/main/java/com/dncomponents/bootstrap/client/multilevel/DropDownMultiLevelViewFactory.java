package com.dncomponents.bootstrap.client.multilevel;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.dropdown.DropDownMultiLevelUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class DropDownMultiLevelViewFactory {

    public static class DefaultDropDownMultiLevelViewFactory extends AbstractPluginHelper implements ViewFactory<DropDownMultiLevelUi> {

        private static DefaultDropDownMultiLevelViewFactory instance;

        private DefaultDropDownMultiLevelViewFactory() {
        }

        public static DefaultDropDownMultiLevelViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultDropDownMultiLevelViewFactory();
            return instance;
        }

        @Override
        public DropDownMultiLevelUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).dropDownMultiLevelUi;
            return new DropDownMultiLevelUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return DropDownMultiLevelUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return DropDownMultiLevelUiImpl.class;
        }
    }

}
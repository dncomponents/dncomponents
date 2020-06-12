package com.dncomponents.material.client.list;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.list.ListUi;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class ListDataViewFactory {

    public static class DefaultListDataViewFactory extends AbstractPluginHelper implements ViewFactory<ListUi> {

        private static DefaultListDataViewFactory instance;

        private DefaultListDataViewFactory() {
        }

        public static DefaultListDataViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultListDataViewFactory();
            return instance;
        }

        @Override
        public ListUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).list;
            return new ListUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return ListUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return ListUiImpl.class;
        }
    }

}
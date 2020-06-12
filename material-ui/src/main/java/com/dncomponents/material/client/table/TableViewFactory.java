package com.dncomponents.material.client.table;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.table.TableUi;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TableViewFactory {


    public static class DefaultTableViewFactory extends AbstractPluginHelper implements ViewFactory<TableUi> {

        private static DefaultTableViewFactory instance;

        private DefaultTableViewFactory() {
        }

        public static DefaultTableViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTableViewFactory();
            return instance;
        }

        @Override
        public TableUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).tableUi;
            return new TableUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return TableUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TableUiImpl.class;
        }
    }

}
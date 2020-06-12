package com.dncomponents.bootstrap.client.table;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.radio.RadioViewImpl;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.table.TableUi;
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
                templateElement = (BootstrapUi.getUi()).tableUi;
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
package com.dncomponents.material.client.tabletree;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tabletree.TableTreeUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TableTreeViewFactory {

    public static class DefaultTableTreeViewFactory extends AbstractPluginHelper implements ViewFactory<TableTreeUi> {

        private static DefaultTableTreeViewFactory instance;

        private DefaultTableTreeViewFactory() {
        }

        public static DefaultTableTreeViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTableTreeViewFactory();
            return instance;
        }

        @Override
        public TableTreeUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                return new TableTreeUiImpl();
            else
                return new TableTreeUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return TableTreeUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TableTreeUiImpl.class;
        }
    }

}
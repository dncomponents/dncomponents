package com.dncomponents.bootstrap.client.tree;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tree.TreeUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TreeViewFactory {

    public static class DefaultTreeViewFactory extends AbstractPluginHelper implements ViewFactory<TreeUi> {

        private static DefaultTreeViewFactory instance;

        private DefaultTreeViewFactory() {
        }

        public static DefaultTreeViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTreeViewFactory();
            return instance;
        }

        @Override
        public TreeUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).tree;
            return new TreeUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return TreeUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TreeUiImpl.class;
        }
    }

}
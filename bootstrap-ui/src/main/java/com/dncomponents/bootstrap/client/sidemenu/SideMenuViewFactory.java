package com.dncomponents.bootstrap.client.sidemenu;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.sidemenu.SideMenuView;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class SideMenuViewFactory {

    public static class DefaultSideMenuViewFactory extends AbstractPluginHelper implements ViewFactory<SideMenuView> {

        private static DefaultSideMenuViewFactory instance;

        private DefaultSideMenuViewFactory() {
        }

        public static DefaultSideMenuViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultSideMenuViewFactory();
            return instance;
        }

        @Override
        public SideMenuView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).sidemenu;
            return new SideMenuViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return SideMenuViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return SideMenuViewImpl.class;
        }
    }

}
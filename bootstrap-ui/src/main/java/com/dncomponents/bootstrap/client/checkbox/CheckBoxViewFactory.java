package com.dncomponents.bootstrap.client.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class CheckBoxViewFactory {


    public static class DefaultCheckBoxViewFactory extends AbstractPluginHelper implements ViewFactory<CheckBoxView> {

        private static DefaultCheckBoxViewFactory instance;

        private DefaultCheckBoxViewFactory() {
        }

        public static DefaultCheckBoxViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultCheckBoxViewFactory();
            return instance;
        }

        @Override
        public CheckBoxView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = BootstrapUi.getUi().checkbox;
            return new CheckBoxViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return CheckBoxViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return CheckBoxViewImpl.class;
        }
    }

    public static class DefaultSimpleCheckBoxViewFactory extends AbstractPluginHelper implements ViewFactory<CheckBoxView> {

        private static DefaultSimpleCheckBoxViewFactory instance;

        private DefaultSimpleCheckBoxViewFactory() {
        }

        public static DefaultSimpleCheckBoxViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultSimpleCheckBoxViewFactory();
            return instance;
        }

        @Override
        public CheckBoxView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = BootstrapUi.getUi().simplecheckbox;
            return new CheckBoxViewImplSimple(templateElement);
        }

        @Override
        public String getId() {
            return CheckBoxViewImplSimple.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return CheckBoxViewImplSimple.class;
        }
    }

}
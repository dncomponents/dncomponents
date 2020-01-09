package com.dncomponents.material.client.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.material.client.MaterialUi;
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
                templateElement = MaterialUi.getUi().checkbox;
            return new MdcCheckBoxViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return MdcCheckBoxViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return MdcCheckBoxViewImpl.class;
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
                templateElement = MaterialUi.getUi().simplecheckbox;
            return new MdcCheckBoxViewImplSimple(templateElement);
        }

        @Override
        public String getId() {
            return MdcCheckBoxViewImplSimple.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return MdcCheckBoxViewImplSimple.class;
        }
    }

}
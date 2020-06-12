package com.dncomponents.bootstrap.client.textbox;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TextBoxViewFactory {


    public static class DefaultTextBoxViewFactory extends AbstractPluginHelper implements ViewFactory<TextBoxView> {

        private static DefaultTextBoxViewFactory instance;

        private DefaultTextBoxViewFactory() {
        }

        public static DefaultTextBoxViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTextBoxViewFactory();
            return instance;
        }

        @Override
        public TextBoxView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).textbox;
            return new TextBoxViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return TextBoxViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TextBoxViewImpl.class;
        }
    }

}
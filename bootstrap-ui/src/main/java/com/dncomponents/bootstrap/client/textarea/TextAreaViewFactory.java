package com.dncomponents.bootstrap.client.textarea;

import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.bootstrap.client.table.TableUiImpl;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TextAreaViewFactory {


    public static class DefaultTextAreaViewFactory extends AbstractPluginHelper implements ViewFactory<TextBoxView> {

        private static DefaultTextAreaViewFactory instance;

        private DefaultTextAreaViewFactory() {
        }

        public static DefaultTextAreaViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultTextAreaViewFactory();
            return instance;
        }

        @Override
        public TextBoxView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (BootstrapUi.getUi()).textarea;
            return new TextAreaViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return TextAreaViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TextAreaViewImpl.class;
        }
    }

}
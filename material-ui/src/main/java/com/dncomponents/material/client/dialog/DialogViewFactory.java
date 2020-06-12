package com.dncomponents.material.client.dialog;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.modal.DialogView;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class DialogViewFactory {

    public static class DefaultDialogViewFactory extends AbstractPluginHelper implements ViewFactory<DialogView> {

        private static DefaultDialogViewFactory instance;

        private DefaultDialogViewFactory() {
        }

        public static DefaultDialogViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultDialogViewFactory();
            return instance;
        }

        @Override
        public DialogView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).modalDialog;
            return new DialogViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return DialogViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return DialogViewImpl.class;
        }
    }

}
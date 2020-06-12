package com.dncomponents.material.client.radio;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class RadioViewFactory {


    public static class DefaultRadioViewFactory extends AbstractPluginHelper implements ViewFactory<RadioView> {

        private static DefaultRadioViewFactory instance;

        private DefaultRadioViewFactory() {
        }

        public static DefaultRadioViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultRadioViewFactory();
            return instance;
        }

        @Override
        public RadioView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).radio;
            return new RadioViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return RadioViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return RadioViewImpl.class;
        }
    }

}
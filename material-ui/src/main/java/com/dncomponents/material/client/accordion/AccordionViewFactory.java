package com.dncomponents.material.client.accordion;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.accordion.AccordionUi;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class AccordionViewFactory {


    public static class DefaultAccordionViewFactory extends AbstractPluginHelper implements ViewFactory<AccordionUi> {

        private static DefaultAccordionViewFactory instance;

        private DefaultAccordionViewFactory() {
        }

        public static DefaultAccordionViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultAccordionViewFactory();
            return instance;
        }

        @Override
        public AccordionUi getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).accordionUi;
            return new AccordionUiImpl(templateElement);
        }

        @Override
        public String getId() {
            return AccordionUiImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return AccordionUiImpl.class;
        }
    }

}
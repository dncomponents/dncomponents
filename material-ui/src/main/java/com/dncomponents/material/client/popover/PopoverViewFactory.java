package com.dncomponents.material.client.popover;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.popover.PopoverView;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class PopoverViewFactory {


    public static class DefaultPopoverViewFactory extends AbstractPluginHelper implements ViewFactory<PopoverView> {

        private static DefaultPopoverViewFactory instance;

        private DefaultPopoverViewFactory() {
        }

        public static DefaultPopoverViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultPopoverViewFactory();
            return instance;
        }

        @Override
        public PopoverView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).popover;
            return new PopoverViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return PopoverViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return PopoverViewImpl.class;
        }
    }

}
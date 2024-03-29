package com.dncomponents.material.client.tooltip;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.tooltip.TooltipView;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class ToolTipViewFactory {


    public static class DefaultToolTipViewFactory extends AbstractPluginHelper implements ViewFactory<TooltipView> {

        private static DefaultToolTipViewFactory instance;

        private DefaultToolTipViewFactory() {
        }

        public static DefaultToolTipViewFactory getInstance() {
            if (instance == null)
                instance = new DefaultToolTipViewFactory();
            return instance;
        }

        @Override
        public TooltipView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            if (templateElement == null)
                templateElement = (MaterialUi.getUi()).tooltip;
            return new TooltipViewImpl(templateElement);
        }

        @Override
        public String getId() {
            return TooltipViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return TooltipViewImpl.class;
        }
    }

}

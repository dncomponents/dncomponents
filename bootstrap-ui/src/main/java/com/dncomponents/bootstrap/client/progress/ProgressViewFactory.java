package com.dncomponents.bootstrap.client.progress;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import elemental2.dom.HTMLTemplateElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nikolasavic
 */
public class ProgressViewFactory {
    public static class DefaultProgressViewFactory extends AbstractPluginHelper implements ViewFactory<ProgressView> {

        private static DefaultProgressViewFactory instance;

        private DefaultProgressViewFactory() {
            arguments.put(ProgressViewImpl.Builder.typeId, ProgressBarTypes.lookUp.toStringList());
            arguments.put(ProgressViewImpl.Builder.colorTypeId, ProgressBarColorTypes.lookUp.toStringList());
        }

        public static DefaultProgressViewFactory getInstance() {
            if (instance == null)
                return instance = new DefaultProgressViewFactory();
            return instance;
        }

        @Override
        public ProgressView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            String typesStringList = attributes.get(ProgressViewImpl.Builder.typeId);
            List<ProgressBarTypes> types = new ArrayList<>();
            if (typesStringList != null) {
                String res[] = typesStringList.split("\\s+");
                for (String re : res) {
                    types.add(ProgressBarTypes.lookUp.getValue(re));
                }
            }
            ProgressBarColorTypes color = ProgressBarColorTypes.lookUp.getValue(attributes.get(ProgressViewImpl.Builder.colorTypeId));
            ProgressViewImpl progressView = ProgressViewImpl.Builder.get().type(types).color(color).build();
            return progressView;
        }

        @Override
        public String getId() {
            return ProgressViewImpl.VIEW_ID;
        }

        @Override
        public Class getClazz() {
            return ProgressViewImpl.class;
        }
    }
}
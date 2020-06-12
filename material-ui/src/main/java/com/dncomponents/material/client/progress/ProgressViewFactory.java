package com.dncomponents.material.client.progress;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import elemental2.dom.DomGlobal;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class ProgressViewFactory extends AbstractPluginHelper implements ViewFactory<ProgressView> {

    private static ProgressViewFactory instance;

    private ProgressViewFactory() {
        arguments.put(ProgressViewImpl.Builder.typeId, ProgressBarTypes.lookUp.toStringList());
    }

    public static ProgressViewFactory getInstance() {
        if (instance == null)
            return instance = new ProgressViewFactory();
        return instance;
    }

    @Override
    public ProgressView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
        ProgressBarTypes type = ProgressBarTypes.lookUp.getValue(attributes.get(ProgressViewImpl.Builder.typeId));
        String buffered = attributes.get(ProgressViewImpl.Builder.bufferedId);
        double bufferedDouble = 0;
        if (buffered != null && !buffered.isEmpty()) {
            try {
                bufferedDouble = Double.parseDouble(buffered);
            } catch (Exception ex) {
                DomGlobal.console.log("Error parsing buffer");
            }
        }

        ProgressViewImpl progressView = ProgressViewImpl.Builder.get().setType(type).setBuffered(bufferedDouble).build();
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

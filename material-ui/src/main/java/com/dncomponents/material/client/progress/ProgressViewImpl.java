package com.dncomponents.material.client.progress;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import com.dncomponents.material.client.MaterialUi;
import com.google.gwt.core.client.GWT;
import elemental2.dom.CSSProperties;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.Map;

public class ProgressViewImpl implements ProgressView {

    public static final String VIEW_ID = "default";

    @UiField
    public HTMLElement root;
    @UiField
    public HTMLElement progressBar;
    @UiField
    public HTMLElement bufferedBar;

    HtmlBinder uiBinder = HtmlBinder.get(ProgressViewImpl.class, this);

    public ProgressViewImpl() {
        uiBinder.setTemplateElement(MaterialUi.getUi().progress);
        uiBinder.bind();
    }

    public ProgressViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setBarWidth(int percent) {
        progressBar.style.setProperty("transform", "scaleX(" + (percent / 100.0) + ")");
    }

    public void setBuffered(double buffered) {
        bufferedBar.style.setProperty("transform", "scaleX(" + buffered + ")");
    }

    @Override
    public void setBarText(String text) {
//        progressBar.textContent = text;
    }

    @Override
    public void setMinimumWidth(int minimumWidth) {
        progressBar.style.minWidth = CSSProperties.MinWidthUnionType.of(minimumWidth + "em");
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    public static class Builder {
        private ProgressBarTypes type;
        private double buffered;
        private HTMLTemplateElement templateElement;


        protected static final String typeId = "type";
        protected static final String bufferedId = "buffered";

        private Builder() {
        }


        public static Builder get() {
            return new Builder();
        }

        public Builder setType(ProgressBarTypes type) {
            this.type = type;
            return this;
        }


        public Builder template(HTMLTemplateElement templateElement) {
            this.templateElement = templateElement;
            return this;
        }

        public Builder setBuffered(double buffered) {
            this.buffered = buffered;
            return this;
        }

        public ProgressViewImpl build() {
            if (templateElement == null)
                templateElement = MaterialUi.getInstance().progress;
            ProgressViewImpl progressView = new ProgressViewImpl(templateElement);
            final String style = type != null ? type.getStyle() : "";
            if (style != null && !style.isEmpty())
                progressView.root.classList.add(style);
            if (buffered != 0)
                progressView.setBuffered(buffered);
            return progressView;
        }
    }


    public static class ProgressViewFactory extends AbstractPluginHelper implements ViewFactory<ProgressView> {

        private static ProgressViewFactory instance;

        private ProgressViewFactory() {
            arguments.put(Builder.typeId, ProgressBarTypes.lookUp.toStringList());
        }

        public static ProgressViewFactory getInstance() {
            if (instance == null)
                return instance = new ProgressViewFactory();
            return instance;
        }

        @Override
        public ProgressView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            ProgressBarTypes type = ProgressBarTypes.lookUp.getValue(attributes.get(Builder.typeId));
            String buffered = attributes.get(Builder.bufferedId);
            double bufferedDouble = 0;
            if (buffered != null && !buffered.isEmpty()) {
                try {
                    bufferedDouble = Double.parseDouble(buffered);
                } catch (Exception ex) {
                    GWT.log("Error parsing buffer");
                }
            }

            ProgressViewImpl progressView = Builder.get().setType(type).setBuffered(bufferedDouble).build();
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
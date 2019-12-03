package com.dncomponents.bootstrap.client.progress;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.HasStyle;
import com.dncomponents.client.views.core.ViewFactory;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import elemental2.dom.CSSProperties;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgressViewImpl implements ProgressView {

    public static final String VIEW_ID = "default";

    @UiField
    public HTMLElement root;
    @UiField
    public HTMLElement progressBar;

    HtmlBinder uiBinder = HtmlBinder.get(ProgressViewImpl.class, this);

    public ProgressViewImpl() {
        uiBinder.setTemplateElement(BootstrapUi.getUi().progress);
        uiBinder.bind();
    }

    public ProgressViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setBarWidth(int percent) {
        progressBar.style.width = CSSProperties.WidthUnionType.of(percent + "%");
    }

    @Override
    public void setBarText(String text) {
        progressBar.textContent = text;
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
        private static final String BASE_STYLE = "progress-bar";
        private List<ProgressBarTypes> types = new ArrayList<>();
        private ProgressBarColorTypes colorType;
        private HTMLTemplateElement templateElement;


        protected static final String typeId = "type";
        protected static final String colorTypeId = "color";

        private Builder() {
        }

        public Builder color(ProgressBarColorTypes colorType) {
            this.colorType = colorType;
            return this;
        }

        public static Builder get() {
            return new Builder();
        }

        public Builder type(List<ProgressBarTypes> types) {
            this.types.addAll(types);
            return this;
        }

        public Builder type(ProgressBarTypes type) {
            this.types.add(type);
            return this;
        }

        public Builder template(HTMLTemplateElement templateElement) {
            this.templateElement = templateElement;
            return this;
        }


        public ProgressViewImpl build() {
            ProgressViewImpl progressView = null;
            if (templateElement == null)
                progressView = (ProgressViewImpl) Ui.get().getProgressView();
            else
                progressView = new ProgressViewImpl(templateElement);
            progressView.progressBar.className = BASE_STYLE + " "
                    + types.stream().map(HasStyle::appendString).collect(Collectors.joining(" ")) + " "
                    + HasStyle.appendString(colorType);
            return progressView;
        }
    }

    public static class ProgressViewFactory extends AbstractPluginHelper implements ViewFactory<ProgressView> {

        private static ProgressViewFactory instance;

        private ProgressViewFactory() {
            arguments.put(Builder.typeId, ProgressBarTypes.lookUp.toStringList());
            arguments.put(Builder.colorTypeId, ProgressBarColorTypes.lookUp.toStringList());
        }

        public static ProgressViewFactory getInstance() {
            if (instance == null)
                return instance = new ProgressViewFactory();
            return instance;
        }

        @Override
        public ProgressView getView(Map<String, String> attributes, HTMLTemplateElement templateElement) {
            String typesStringList = attributes.get(Builder.typeId);
            List<ProgressBarTypes> types = new ArrayList<>();
            if (typesStringList != null) {
                String res[] = typesStringList.split("\\s+");
                for (String re : res) {
                    types.add(ProgressBarTypes.lookUp.getValue(re));
                }
            }
            ProgressBarColorTypes color = ProgressBarColorTypes.lookUp.getValue(attributes.get(Builder.colorTypeId));
            ProgressViewImpl progressView = Builder.get().type(types).color(color).build();
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
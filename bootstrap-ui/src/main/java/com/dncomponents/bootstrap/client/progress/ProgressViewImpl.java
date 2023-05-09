/*
 * Copyright 2023 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.bootstrap.client.progress;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.bootstrap.client.BootstrapUi;
import com.dncomponents.client.views.core.HasStyle;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import elemental2.dom.CSSProperties;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProgressViewImpl implements ProgressView {

    public static final String VIEW_ID = "default";

    @UiField
    public HTMLElement root;
    @UiField
    public HTMLElement progressBar;

    HtmlBinder uiBinder = HtmlBinder.create(ProgressViewImpl.class, this);

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
            ProgressViewImpl progressView;
            if (templateElement == null)
                progressView = (ProgressViewImpl) BootstrapUi.getUi().getProgressView();
            else
                progressView = new ProgressViewImpl(templateElement);
            progressView.progressBar.className = BASE_STYLE + " "
                    + types.stream().map(HasStyle::appendString).collect(Collectors.joining(" ")) + " "
                    + HasStyle.appendString(colorType);
            return progressView;
        }
    }

}

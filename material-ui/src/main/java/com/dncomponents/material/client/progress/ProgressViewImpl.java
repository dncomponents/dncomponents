/*
 * Copyright 2024 dncomponents
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

package com.dncomponents.material.client.progress;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.core.ui.progress.ProgressView;
import com.dncomponents.material.client.MaterialUi;
import elemental2.dom.CSSProperties;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class ProgressViewImpl implements ProgressView {

    public static final String VIEW_ID = "default";

    @UiField
    public HTMLElement root;
    @UiField
    public HTMLElement progressBar;
    @UiField
    public HTMLElement bufferedBar;

    HtmlBinder uiBinder = HtmlBinder.create(ProgressViewImpl.class, this);

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
                templateElement = MaterialUi.getUi().progress;
            ProgressViewImpl progressView = new ProgressViewImpl(templateElement);
            final String style = type != null ? type.getStyle() : "";
            if (style != null && !style.isEmpty())
                progressView.root.classList.add(style);
            if (buffered != 0)
                progressView.setBuffered(buffered);
            return progressView;
        }
    }


}

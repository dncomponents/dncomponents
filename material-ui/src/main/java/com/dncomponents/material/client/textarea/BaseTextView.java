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

package com.dncomponents.material.client.textarea;

import com.dncomponents.UiField;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.KeyDownHandler;
import com.dncomponents.client.dom.handlers.KeyUpHandler;
import com.dncomponents.client.dom.handlers.OnBlurHandler;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import com.dncomponents.material.client.textbox.TextBoxBuilderConst;
import com.dncomponents.material.client.textbox.TextBoxProperty;
import com.dncomponents.material.client.textbox.TextBoxType;
import elemental2.dom.*;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseTextView implements TextBoxView {

    public static final String VIEW_ID = "default";
    @UiField
    protected HTMLElement wrapperRoot;
    @UiField
    public HTMLElement root;
    @UiField
    protected HTMLElement filledLabel;
    @UiField
    protected HTMLElement ripple;
    //outlined
    @UiField
    protected HTMLElement outLinedLabel;
    @UiField
    protected HTMLElement notchedPanel;
    //helper panel
    @UiField
    protected HTMLElement helperPanel;
    @UiField
    protected HTMLElement helperText;
    @UiField
    protected HTMLElement characterCounter;
    //current
    protected HTMLElement currentLabel;
    protected HTMLElement currentRoot;
    protected int maxCharacter;


    protected void init() {
        getInputElement().addEventListener("focus", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                root.classList.add(TextBoxBuilderConst.getInstance().focused);
                moveLabelToTop(true);
                if (ripple != null)
                    ripple.classList.add(TextBoxBuilderConst.getInstance().lineRippleActive);
            }
        });
        getInputElement().addEventListener("blur", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                root.classList.remove(TextBoxBuilderConst.getInstance().focused);
                if (getInputElementValue().isEmpty())
                    moveLabelToTop(false);
                if (ripple != null)
                    ripple.classList.remove(TextBoxBuilderConst.getInstance().lineRippleActive);
            }
        });
    }

    private void moveLabelToTop(boolean b) {
        if (b) {
            currentLabel.classList.add(TextBoxBuilderConst.getInstance().floatingLabelAbove);
            if (notchedPanel != null)
                notchedPanel.classList.add(TextBoxBuilderConst.getInstance().outlineNotched);
        } else {
            currentLabel.classList.remove(TextBoxBuilderConst.getInstance().floatingLabelAbove);
            if (notchedPanel != null)
                notchedPanel.classList.remove(TextBoxBuilderConst.getInstance().outlineNotched);
        }
    }

    public void setLabel(String label) {
        if (currentLabel != null && label != null)
            currentLabel.innerHTML = label;
    }

    @Override
    public String getValue() {
        return getInputElementValue();
    }

    @Override
    public void setValue(String value) {
        moveLabelToTop(value != null && !value.isEmpty());
        setInputElementValue(value);
    }

    @Override
    public void addOnInputChangeHandler(EventListener listener) {

    }

    @Override
    public void addOnBlurHandler(OnBlurHandler handler) {
        getInputElement().addEventListener(handler.getType(), handler);
    }

    @Override
    public void addOnKeyUpHandler(KeyUpHandler handler) {
        getInputElement().addEventListener(handler.getType(), handler);
    }

    @Override
    public void setError(boolean b) {
        if (b) {
            root.classList.add(TextBoxBuilderConst.getInstance().invalid);
            helperText.classList.add(TextBoxBuilderConst.getInstance().invalidTextMessage);
        } else {
            helperText.classList.remove(TextBoxBuilderConst.getInstance().invalidTextMessage);
            root.classList.remove(TextBoxBuilderConst.getInstance().invalid);
        }
    }

    @Override
    public void setErrorMessage(String errorMessage) {
//        if (helperText != null)
//            helperText.innerHTML = errorMessage;
    }

    @Override
    public void setPlaceHolder(String placeHolder) {

    }

    @Override
    public HTMLElement asElement() {
        return currentRoot;
    }

    @Override
    public HTMLElement getFocusElement() {
        return getInputElement();
    }

    protected void setTextCount(int max) {
        this.maxCharacter = max;
        setCounterText();
        DomUtil.addHandler(getInputElement(), new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyboardEvent event) {
                if (getInputElementValue().length() >= maxCharacter) {
                    if (!event.code.equals("Backspace"))
                        event.preventDefault();
                    return;
                }
                DomGlobal.setTimeout(e -> {
                    if (getInputElementValue().length() <= maxCharacter)
                        setCounterText();
                }, 100);
            }
        });
    }

    private void setCounterText() {
        characterCounter.innerHTML = getInputElementValue().length() + "/" + maxCharacter;
    }

    protected abstract HTMLElement getInputElement();

    protected abstract String getInputElementValue();

    protected abstract void setInputElementValue(String value);

    public abstract static class BaseTextViewBuilder<C extends BaseTextViewBuilder> {

        private List<TextBoxProperty> textBoxProperties = new ArrayList<>();

        protected HTMLTemplateElement templateElement;
        protected TextBoxType type;
        private String label;
        private String helperText;
        private int maxCharacter;

        //parser
        public static final String typeId = "type";
        public static final String labelId = "label";
        public static final String helperTextId = "helpertext";
        protected static final String propertyId = "prop";
        public static final String characterCounterId = "charactercounter";

        public BaseTextViewBuilder() {
        }


        public C setTextBoxProperties(List<TextBoxProperty> textBoxProperties) {
            this.textBoxProperties = textBoxProperties;
            return (C) this;
        }

        public C setTemplateElement(HTMLTemplateElement templateElement) {
            this.templateElement = templateElement;
            return (C) this;
        }

        public C setType(TextBoxType type) {
            this.type = type;
            return (C) this;
        }

        public C setLabel(String label) {
            this.label = label;
            return (C) this;
        }

        public C setHelperText(String helperText) {
            this.helperText = helperText;
            return (C) this;
        }

        public C setCharacterCounter(int max) {
            this.maxCharacter = max;
            return (C) this;
        }

        protected void init(BaseTextView v) {
            if (type == TextBoxType.FILLED) {
                v.currentLabel = v.filledLabel;
                v.notchedPanel.remove();
                if (checkLabel(v))
                    v.currentLabel.remove();
            } else {
                v.currentLabel = v.outLinedLabel;
                v.root.classList.add(TextBoxBuilderConst.getInstance().outlined);
                v.ripple.remove();
                if (checkLabel(v))
                    ((HTMLElement) v.currentLabel.parentNode).remove();
            }
            v.setLabel(label);
            if (maxCharacter == 0 && helperText == null) {
                v.currentRoot = v.root;
                v.helperPanel.remove();
            }
            if (helperText != null)
                v.helperText.innerHTML = helperText;

            if (maxCharacter > 0)
                v.setTextCount(maxCharacter);
            else
                v.characterCounter.remove();
        }

        private boolean checkLabel(BaseTextView v) {
            if (label == null || label.isEmpty()) {
                v.root.classList.add(TextBoxBuilderConst.getInstance().noLabel);
                return true;
            }
            return false;
        }
    }

}

package com.dncomponents.material.client.textarea;

import com.dncomponents.UiField;
import com.dncomponents.UiStyle;
import com.dncomponents.UiTemplate;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.textbox.TextBoxType;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;
import elemental2.dom.HTMLTextAreaElement;

/**
 * @author nikolasavic
 */
@UiTemplate
public class TextAreaViewImpl extends BaseTextView {

    @UiStyle
    String focusTextAreaStyle;
    @UiStyle
    String focusFloatingLabelStyle;
    //
    @UiField
    protected HTMLTextAreaElement inputElement;
    HtmlBinder uiBinder = HtmlBinder.get(TextAreaViewImpl.class, this);

    public TextAreaViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        currentRoot = wrapperRoot;
        init();
    }

    private void setBorderFocused(boolean b) {
        if (b) {
            root.classList.add(focusTextAreaStyle);
        } else {
            root.classList.remove(focusTextAreaStyle);
        }
    }

    @Override
    protected HTMLElement getInputElement() {
        return inputElement;
    }

    @Override
    protected String getInputElementValue() {
        return inputElement.value;
    }

    @Override
    protected void setInputElementValue(String value) {
        inputElement.value = value;
    }

    public static class TextAreaViewBuilder extends BaseTextViewBuilder<TextAreaViewBuilder> {

        public static TextAreaViewBuilder get() {
            return new TextAreaViewBuilder();
        }

        public TextAreaViewBuilder() {
        }

        @Override
        protected void init(BaseTextView v) {
            if (type == null)
                type = TextBoxType.OUTLINED;
            super.init(v);
        }

        public <B extends BaseTextView> B build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().textarea;
            final TextAreaViewImpl view = new TextAreaViewImpl(templateElement);
            init(view);
            return (B) view;
        }
    }

}
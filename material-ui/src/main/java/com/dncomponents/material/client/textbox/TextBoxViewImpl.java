package com.dncomponents.material.client.textbox;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.textarea.BaseTextView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class TextBoxViewImpl extends BaseTextView {

    @UiField
    HTMLInputElement inputElement;
    //icons
    @UiField
    HTMLElement leadingIcon;
    @UiField
    HTMLElement trailingIcon;


    HtmlBinder uiBinder = HtmlBinder.get(TextBoxViewImpl.class, this);

    private TextBoxViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }


    private TextBoxViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
        currentRoot = wrapperRoot;
        init();
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

    public static class TextBoxViewBuilder extends BaseTextViewBuilder<TextBoxViewBuilder> {

        private MaterialIcons leadingIcon;
        private MaterialIcons trailingIcon;

        protected static final String leadingIconId = "leadingicon";
        protected static final String trailingIconId = "trailingicon";

        public static TextBoxViewBuilder get() {
            return new TextBoxViewBuilder();
        }

        public TextBoxViewBuilder() {
        }

        public TextBoxViewBuilder setLeadingIcon(MaterialIcons leadingIcon) {
            this.leadingIcon = leadingIcon;
            return this;
        }

        public TextBoxViewBuilder setTrailingIcon(MaterialIcons trailingIcon) {
            this.trailingIcon = trailingIcon;
            return this;
        }

        @Override
        protected void init(BaseTextView v) {
            if (type == null)
                type = TextBoxType.FILLED;
            initIcon(leadingIcon, v.root, ((TextBoxViewImpl) v).leadingIcon, TextBoxBuilderConst.getInstance().withLeadingIcon);
            initIcon(trailingIcon, v.root, ((TextBoxViewImpl) v).trailingIcon, TextBoxBuilderConst.getInstance().withTrailingIcon);
            super.init(v);
        }

        public <B extends BaseTextView> B build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().textbox;
            final TextBoxViewImpl view = new TextBoxViewImpl(templateElement);
            init(view);
            return (B) view;
        }


        private void initIcon(MaterialIcons icon, HTMLElement root, HTMLElement iconPanel, String style) {
            if (icon == null) {
                if (iconPanel != null)
                    iconPanel.remove();
            } else {
                iconPanel.textContent = icon.getStyle();
                root.classList.add(style);
            }
        }

    }
}
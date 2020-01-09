package com.dncomponents.material.client.textbox;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.textarea.MdcBaseTextView;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import elemental2.dom.HTMLTemplateElement;

/**
 * @author nikolasavic
 */
public class MdcTextBoxViewImpl extends MdcBaseTextView {

    @UiField
    HTMLInputElement inputElement;
    //icons
    @UiField
    HTMLElement leadingIcon;
    @UiField
    HTMLElement trailingIcon;


    HtmlBinder uiBinder = HtmlBinder.get(MdcTextBoxViewImpl.class, this);

    private MdcTextBoxViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }


    private MdcTextBoxViewImpl(HTMLTemplateElement templateElement) {
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

    public static class MdcTextBoxViewBuilder extends MdcBaseTextViewBuilder<MdcTextBoxViewBuilder> {

        private MaterialIcons leadingIcon;
        private MaterialIcons trailingIcon;

        protected static final String leadingIconId = "leadingicon";
        protected static final String trailingIconId = "trailingicon";

        public static MdcTextBoxViewBuilder get() {
            return new MdcTextBoxViewBuilder();
        }

        public MdcTextBoxViewBuilder() {
        }

        public MdcTextBoxViewBuilder setLeadingIcon(MaterialIcons leadingIcon) {
            this.leadingIcon = leadingIcon;
            return this;
        }

        public MdcTextBoxViewBuilder setTrailingIcon(MaterialIcons trailingIcon) {
            this.trailingIcon = trailingIcon;
            return this;
        }

        @Override
        protected void init(MdcBaseTextView v) {
            if (type == null)
                type = TextBoxType.FILLED;
            initIcon(leadingIcon, v.root, ((MdcTextBoxViewImpl) v).leadingIcon, TextBoxBuilderConst.getInstance().withLeadingIcon);
            initIcon(trailingIcon, v.root, ((MdcTextBoxViewImpl) v).trailingIcon, TextBoxBuilderConst.getInstance().withTrailingIcon);
            super.init(v);
        }

        public <B extends MdcBaseTextView> B build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().textbox;
            final MdcTextBoxViewImpl view = new MdcTextBoxViewImpl(templateElement);
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
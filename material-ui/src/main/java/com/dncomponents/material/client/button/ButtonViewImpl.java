package com.dncomponents.material.client.button;

import com.dncomponents.UiField;
import com.dncomponents.client.components.core.HtmlBinder;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.core.ui.button.ButtonView;
import com.dncomponents.material.client.MaterialUi;
import com.dncomponents.material.client.textbox.MaterialIcons;
import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTemplateElement;

public class ButtonViewImpl implements ButtonView {

    public static final String VIEW_ID = "default";
    @UiField
    HTMLButtonElement root;
    @UiField
    HTMLElement iconPanel;
    @UiField
    HTMLElement textPanel;

    HtmlBinder uiBinder = HtmlBinder.get(ButtonViewImpl.class, this);

    private ButtonViewImpl(String template) {
        uiBinder.setTemplateContent(template);
        uiBinder.bind();
    }

    private ButtonViewImpl(HTMLTemplateElement templateElement) {
        uiBinder.setTemplateElement(templateElement);
        uiBinder.bind();
    }

    @Override
    public void setText(String text) {
        textPanel.innerHTML = text;
    }

    @Override
    public void setHTML(String html) {
        textPanel.innerHTML = html;
    }

    @Override
    public String getHTML() {
        return textPanel.innerHTML;
    }

    @Override
    public String getText() {
        return textPanel.textContent;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (!enabled)
            root.setAttribute("disabled", "");
        else
            root.removeAttribute("disabled");
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    @Override
    public MainViewSlots getViewSlots() {
        return () -> textPanel;
    }


    public static class ButtonBuilder {

        private static final String BASE_STYLE = ButtonBuilderConst.getInstance().base;

        private HTMLTemplateElement templateElement;

        private ButtonType type;
        private MaterialIcons icon;
        private String label;

        //parser
        protected static final String typeId = "type";
        protected static final String iconId = "icon";
        protected static final String labelId = "label";

        private ButtonBuilder() {
        }

        public static ButtonBuilder get() {
            return new ButtonBuilder();
        }

        public ButtonBuilder type(ButtonType buttonType) {
            this.type = buttonType;
            return this;
        }

        public ButtonBuilder setIcon(MaterialIcons icon) {
            this.icon = icon;
            return this;
        }

        public ButtonBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public ButtonBuilder template(HTMLTemplateElement templateElement) {
            this.templateElement = templateElement;
            return this;
        }

        public ButtonViewImpl build() {
            if (templateElement == null)
                templateElement = MaterialUi.getUi().button;
            ButtonViewImpl view = new ButtonViewImpl(templateElement);
            if (type == null)
                type = ButtonType.TEXT;
            if (!type.getStyle().isEmpty())
                view.root.classList.add(type.getStyle());
            if (icon == null)
                view.iconPanel.remove();
            else
                view.iconPanel.innerHTML = icon.getStyle();
            if (label != null)
                view.textPanel.innerHTML = label;
            return view;
        }
    }

}
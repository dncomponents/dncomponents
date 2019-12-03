package com.dncomponents.client.components.textarea;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.textbox.TextBox;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.Element;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class TextArea extends TextBox {

    public TextArea() {
        super(Ui.get().getTextAreaView());
    }

    public TextArea(TextBoxView view) {
        super(view);
    }

    public void append(String text) {
        view.setValue(text + (getValueFromView() == null ? "" : getValueFromView()));
    }

    public static class TextAreaHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static TextAreaHtmlParser instance;

        private TextAreaHtmlParser() {
        }

        public static TextAreaHtmlParser getInstance() {
            if (instance == null)
                return instance = new TextAreaHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            TextArea textArea = new TextArea();
            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                textArea.setValue(value);
            }
            replaceAndCopy(htmlElement, textArea);
            return textArea;
        }

        @Override
        public String getId() {
            return "dn-text-area";
        }

        @Override
        public Class getClazz() {
            return TextArea.class;
        }
    }

}

package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import com.google.gwt.core.client.GWT;
import elemental2.dom.Element;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class IntegerBox extends ValueBox<Integer> {

    public IntegerBox() {
        super(Ui.get().getTextBoxView());
    }

    public IntegerBox(TextBoxView view) {
        super(view);
    }

    @Override
    Integer parseString(String str) {
        Integer result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            result = null;
        }
        return result;
    }

    @Override
    String render(Integer value) {
        return value == null ? "" : value.toString();
    }


    public static class IntegerBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static IntegerBoxHtmlParser instance;

        private IntegerBoxHtmlParser() {
        }

        public static IntegerBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new IntegerBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            IntegerBox integerBox = new IntegerBox();
            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                try {
                    integerBox.setValue(Integer.parseInt(value));
                } catch (Exception ex) {
                    GWT.log("Warning: error parsing integer value: " + value);
                }
            }
            replaceAndCopy(htmlElement, integerBox);
            return integerBox;
        }

        @Override
        public String getId() {
            return "dn-integer-box";
        }

        @Override
        public Class getClazz() {
            return IntegerBox.class;
        }
    }

}

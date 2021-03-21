package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class DoubleBox extends ValueBox<Double> {

    public DoubleBox() {
        super(Ui.get().getTextBoxView());
    }

    public DoubleBox(TextBoxView view) {
        super(view);
    }

    @Override
    Double parseString(String str) throws ValidationException {
        Double result;
        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException ex) {
            throw new ValidationException(ex.getMessage());
        }
        return result;
    }

    @Override
    String render(Double value) {
        return value == null ? "" : value.toString();
    }


    public static class DoubleBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {


        private static DoubleBoxHtmlParser instance;

        private DoubleBoxHtmlParser() {
        }

        public static DoubleBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new DoubleBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            DoubleBox doubleBox;
            TextBoxView view = getView(TextBox.class, htmlElement, elements);
            if (view != null)
                doubleBox = new DoubleBox(view);
            else
                doubleBox = new DoubleBox();

            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                try {
                    doubleBox.setValue(Double.parseDouble(value));
                } catch (Exception ex) {
                    DomGlobal.console.warn("Warning: error parsing double value: " + value);
                }
            }

            replaceAndCopy(htmlElement, doubleBox);
            return doubleBox;
        }

        @Override
        public String getId() {
            return "dn-double-box";
        }

        @Override
        public Class getClazz() {
            return DoubleBox.class;
        }
    }

}

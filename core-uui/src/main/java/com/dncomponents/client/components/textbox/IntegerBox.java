package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
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
    Integer parseString(String str) throws ValidationException {
        Integer result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            throw new ValidationException(ex.getMessage());
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
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            IntegerBox integerBox;
            TextBoxView view = getView(TextBox.class, htmlElement, elements);
            if (view != null)
                integerBox = new IntegerBox(view);
            else
                integerBox = new IntegerBox();
            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                try {
                    integerBox.setValue(Integer.parseInt(value));
                } catch (Exception ex) {
                    integerBox.getView().setError(true);
                    integerBox.getView().setErrorMessage("error parsing integer value");
//                    DomGlobal.console.log("Warning: error parsing integer value: " + value);
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

package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import com.google.gwt.core.client.GWT;
import elemental2.dom.Element;

import java.util.Map;

/**
 * @author nikolasavic
 */
public class LongBox extends ValueBox<Long> {

    public LongBox() {
        super(Ui.get().getTextBoxView());
    }

    public LongBox(TextBoxView view) {
        super(view);
    }

    @Override
    Long parseString(String str) {
        Long result;
        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException ex) {
            result = null;
        }
        return result;
    }

    @Override
    String render(Long value) {
        return value == null ? "" : value.toString();
    }

    public static class LongBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {


        private static LongBoxHtmlParser instance;

        private LongBoxHtmlParser() {
        }

        public static LongBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new LongBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            LongBox longBox = new LongBox();
            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                try {
                    longBox.setValue(Long.parseLong(value));
                } catch (Exception ex) {
                    GWT.log("Warning: error parsing long value: " + value);
                }
            }
            replaceAndCopy(htmlElement, longBox);
            return longBox;
        }

        @Override
        public String getId() {
            return "dn-long-box";
        }

        @Override
        public Class getClazz() {
            return LongBox.class;
        }
    }

}

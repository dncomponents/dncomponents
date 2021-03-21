package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import elemental2.core.JsDate;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;

import java.util.Date;
import java.util.Map;

public class DateBox extends ValueBox<Date> {


    public DateBox() {
        super(Ui.get().getTextBoxView());
    }

    public DateBox(TextBoxView view) {
        super(view);
    }


    @Override
    public Date parseString(String dateText) throws ValidationException {
        Date date = null;
        try {
            if (dateText.length() > 0) {
                final double jsDate = JsDate.parse(dateText);
                date = new Date(new Double(jsDate).longValue());
            }
        } catch (Exception ex) {
            throw new ValidationException(ex.getMessage());
        }
        return date;
    }

    @Override
    String render(Date value) {
        return value == null ? "" : new JsDate(((double) value.getTime())).toLocaleDateString();
//        return value == null ? "" : new JsDate((double)value.getTime()).toISOString().split("T")[0];
    }


//    public void setDateTimeFormat(DateTimeFormat dateTimeFormat) { //todo
//        this.dateTimeFormat = dateTimeFormat;
//    }


    public static class DateBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static DateBoxHtmlParser instance;

        private DateBoxHtmlParser() {
        }

        public static DateBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new DateBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            DateBox dateBox;
            TextBoxView view = getView(TextBox.class, htmlElement, elements);
            if (view != null)
                dateBox = new DateBox(view);
            else
                dateBox = new DateBox();
            String value = htmlElement.getAttribute(VALUE);
            if (value != null) {
                try {
                    dateBox.setValue(dateBox.parseString(value));
                } catch (Exception ex) {
                    DomGlobal.console.warn("Warning: error parsing date value: " + value);
                }
            }

            replaceAndCopy(htmlElement, dateBox);
            return dateBox;
        }

        @Override
        public String getId() {
            return "dn-date-box";
        }

        @Override
        public Class getClazz() {
            return DateBox.class;
        }
    }

}

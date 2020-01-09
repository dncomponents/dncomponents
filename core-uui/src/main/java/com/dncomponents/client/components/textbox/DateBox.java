package com.dncomponents.client.components.textbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.textbox.TextBoxView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import elemental2.dom.Element;

import java.util.Date;
import java.util.Map;

public class DateBox extends ValueBox<Date> {

    private DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);

    public DateBox() {
        super(Ui.get().getTextBoxView());
    }

    public DateBox(TextBoxView view) {
        super(view);
    }


    @Override
    public Date parseString(String dateText) {
        Date date = null;
        try {
            if (dateText.length() > 0) {
                date = dateTimeFormat.parse(dateText);
            }
        } catch (IllegalArgumentException exception) {
            try {
                date = new Date(dateText);
                date = dateTimeFormat.parse(dateTimeFormat.format(date));
            } catch (IllegalArgumentException e) {
//                if (reportError) {
//                    dateBox.addStyleName(DATE_BOX_FORMAT_ERROR);
//                }
                return null;
            }
        }
        return date;
    }

    @Override
    String render(Date value) {
        return value == null ? "" : dateTimeFormat.format(value);
    }


    public void setDateTimeFormat(DateTimeFormat dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }


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
                    GWT.log("Warning: error parsing date value: " + value);
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

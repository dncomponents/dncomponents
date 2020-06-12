package com.dncomponents.client.components.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.MainRenderer;
import com.dncomponents.client.views.MainViewSlots;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.radio.RadioView;
import elemental2.dom.Element;
import elemental2.dom.Event;

import java.util.Collections;
import java.util.Map;

import static com.dncomponents.client.components.checkbox.CheckBox.CheckBoxHtmlParser.setValueCh;

/**
 * Created by nikolasavic
 */
public class Radio<T> extends AbstractCheckBox<T> {

    RadioSelectionGroup<T> group;

    public Radio() {
        super(Ui.get().getRadioView());
        bind();
    }

    public Radio(RadioView radioView) {
        super(radioView);
        bind();
    }


    public Radio(T userObject) {
        this();
        setUserObject(userObject);
        setLabel(userObject.toString());
    }

    public Radio(T userObject, RadioSelectionGroup<T> group) {
        this(userObject);
        setGroup(group);
    }

    public Radio(String label, T userObject) {
        this();
        setLabel(label);
        this.userObject = userObject;
    }

    public void setGroup(RadioSelectionGroup<T> group) {
        this.group = group;
        view.setName(group.getGroupName());
        setRenderer(group.getDefaultRenderer());
    }

    protected void bind() {
        view.addOnChangeHandler(new OnChangeHandler() {
            @Override
            public void onChange(Event inputEvent) {
                fromView = true;
                setValue(view.isChecked(), true);
                if (group != null) {
                    group.setSelected(Radio.this, view.isChecked(), true);
                }
                fromView = false;
            }
        });
    }


    //uibinder field
    protected String groupUiField;

    public static class RadioHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static Radio.RadioHtmlParser instance;

        private static final String VALUE = "value";

        private RadioHtmlParser() {
            arguments.put(VALUE, Collections.emptyList());
            arguments.put(CheckBox.CheckBoxHtmlParser.GROUP, Collections.emptyList());
            arguments.put(CheckBox.CheckBoxHtmlParser.DISABLED, Collections.emptyList());
        }

        public static Radio.RadioHtmlParser getInstance() {
            if (instance == null)
                return instance = new Radio.RadioHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            RadioView viewC = getView(Radio.class, htmlElement, templateElement);
            Radio<ItemId> radio;

            if (viewC != null)
                radio = new Radio(viewC);
            else
                radio = new Radio();
            radio.setRenderer(new MainRenderer<ItemId>() {
                @Override
                public void render(ItemId itemId, MainViewSlots slots) {
                    slots.getMainSlot().innerHTML = itemId.getContent();
                }
            });
            setValueCh(htmlElement, radio);
            radio.setUserObject(getIdItem(htmlElement));
            radio.setEnabled(!htmlElement.hasAttribute(CheckBox.CheckBoxHtmlParser.DISABLED));
            radio.groupUiField = htmlElement.getAttribute(CheckBox.CheckBoxHtmlParser.GROUP);
            DomUtil.copyAllAttributes(htmlElement, radio.asElement());
            DomUtil.replace(radio.asElement(), htmlElement);
            radio.setValue(radio.getValue());
            return radio;
        }

        @Override
        public String getId() {
            return "dn-radio";
        }

        @Override
        public Class getClazz() {
            return Radio.class;
        }

    }

}
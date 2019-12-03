package com.dncomponents.client.components.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.HtmlParser;
import com.dncomponents.client.components.core.TemplateParser;
import com.dncomponents.client.components.core.selectionmodel.AbstractSingleSelectionGroup;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxViewSlots;
import elemental2.dom.Element;
import elemental2.dom.NodeList;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RadioSelectionGroup<T> extends AbstractSingleSelectionGroup<T, Radio<T>> {

    private static int id = 0;
    private String groupName;

    public RadioSelectionGroup() {
        setGroupName("radioGroup-" + id++);
    }

    public RadioSelectionGroup(String groupName) {
        setGroupName(groupName);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void addEntityItems(List<T> items) {
        items.forEach(t -> addItem(new Radio<T>(t, this)));
    }

    public void addEntityItems(List<T> items, Radio.RadioRenderer<T> defaultRenderer) {
        setDefaultRenderer(defaultRenderer);
        addEntityItems(items);
    }

    Radio.RadioRenderer<T> defaultRenderer = new Radio.RadioRenderer<T>() {
        @Override
        public void render(T t, CheckBoxViewSlots slots) {
            slots.getMainSlot().innerHTML = t + "";
        }
    };

    public Radio.RadioRenderer<T> getDefaultRenderer() {
        return defaultRenderer;
    }

    public void setDefaultRenderer(Radio.RadioRenderer<T> defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    @Override
    public void setSelectedInView(Radio<T> model, boolean b) {
        model.setValue(b);
    }

    @Override
    public void addItem(Radio<T> value) {
        super.addItem(value);
        value.setGroup(this);
        if (value.isValueTrue()) {
            setSelected(value, true);
        }
    }

    // ui binder field
    protected String uiField;

    public static class RadioSelectionGroupHtmlParser extends AbstractPluginHelper implements HtmlParser<RadioSelectionGroup> {

        private static RadioSelectionGroupHtmlParser instance;

        private RadioSelectionGroupHtmlParser() {
        }

        public static RadioSelectionGroupHtmlParser getInstance() {
            if (instance == null)
                return instance = new RadioSelectionGroupHtmlParser();
            return instance;
        }

        @Override
        public RadioSelectionGroup parse(Element htmlElement, Map<String, ?> elements) {
            String uiField = htmlElement.getAttribute(TemplateParser.KEY);
            RadioSelectionGroup radioSelectionGroup = new RadioSelectionGroup();
            radioSelectionGroup.uiField = uiField;
            elements.values().forEach((Consumer<Object>) o -> {
                if (o instanceof Radio) {
                    Radio radio = (Radio) o;
                    if (radio.groupUiField != null && radio.groupUiField.equals(uiField)) {
                        radioSelectionGroup.addItem((Radio) o);
                    }
                }
            });
            NodeList<Element> elements2 = htmlElement.querySelectorAll("*");
            for (int i = 0; i < elements2.length; i++) {
                Element at = elements2.getAt(i);
                Object o = BaseComponent.allComponents.get(at);
                if (o instanceof Radio) {
                    Radio radio = (Radio) o;
                    if (DomUtil.isDescendant(htmlElement, radio.asElement())) {
                        radioSelectionGroup.addItem(((Radio) o));
                    }
                }
            }
            DomUtil.unwrap(htmlElement);
            return radioSelectionGroup;
        }

        @Override
        public String getId() {
            return "dn-radio-selection-group";
        }

        @Override
        public Class getClazz() {
            return RadioSelectionGroup.class;
        }
    }
}
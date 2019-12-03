package com.dncomponents.client.components.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.HtmlParser;
import com.dncomponents.client.components.core.TemplateParser;
import com.dncomponents.client.components.core.selectionmodel.AbstractMultiSelectionGroup;
import com.dncomponents.client.components.core.selectionmodel.DefaultMultiSelectionModel;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxViewSlots;
import elemental2.dom.Element;
import elemental2.dom.NodeList;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CheckBoxSelectionGroup<T> extends AbstractMultiSelectionGroup<T, CheckBox<T>> {

    private static int id = 0;
    private String groupName;


    public CheckBoxSelectionGroup() {
        setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        setGroupName("checkboxGroup-" + id++);
    }

    public CheckBoxSelectionGroup(String groupName) {
        setSelectionMode(DefaultMultiSelectionModel.SelectionMode.MULTI);
        setGroupName(groupName);
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public void addItem(CheckBox<T> value) {
        super.addItem(value);
        value.setGroup(this);
    }

    public void addEntityItems(List<T> items) {
        items.forEach(t -> addItem(new CheckBox<T>(t, this)));
    }

    public void addEntityItems(List<T> items, CheckBox.CheckBoxRenderer<T> defaultRenderer) {
        setDefaultRenderer(defaultRenderer);
        addEntityItems(items);
    }


    @Override
    public void setSelectedInView(CheckBox<T> model, boolean b) {
        model.setValue(b);
    }

    // ui binder field
    protected String uiField;

    CheckBox.CheckBoxRenderer<T> defaultRenderer = new CheckBox.CheckBoxRenderer<T>() {
        @Override
        public void render(T t, CheckBoxViewSlots slots) {
            slots.getMainSlot().textContent = t + "";
        }
    };

    public void setDefaultRenderer(CheckBox.CheckBoxRenderer<T> defaultRenderer) {
        this.defaultRenderer = defaultRenderer;
    }

    public CheckBox.CheckBoxRenderer<T> getDefaultRenderer() {
        return defaultRenderer;
    }

    public static class CheckBoxSelectionGroupHtmlParser extends AbstractPluginHelper implements HtmlParser<CheckBoxSelectionGroup> {

        private static CheckBoxSelectionGroupHtmlParser instance;

        private CheckBoxSelectionGroupHtmlParser() {
        }

        public static CheckBoxSelectionGroupHtmlParser getInstance() {
            if (instance == null)
                return instance = new CheckBoxSelectionGroupHtmlParser();
            return instance;
        }

        @Override
        public CheckBoxSelectionGroup parse(Element htmlElement, Map<String, ?> elements) {
            String uiField = htmlElement.getAttribute(TemplateParser.KEY);
            CheckBoxSelectionGroup checkBoxSelectionGroup = new CheckBoxSelectionGroup();
            checkBoxSelectionGroup.uiField = uiField;
            elements.values().forEach((Consumer<Object>) o -> {
                if (o instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) o;
                    if (checkBox.groupUiField != null && checkBox.groupUiField.equals(uiField)) {
                        checkBoxSelectionGroup.addItem((CheckBox) o);
                    }
                }
            });
            NodeList<Element> elements2 = htmlElement.querySelectorAll("*");
            for (int i = 0; i < elements2.length; i++) {
                Element at = elements2.getAt(i);
                Object o = BaseComponent.allComponents.get(at);
                if (o instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) o;
                    if (DomUtil.isDescendant(htmlElement, checkBox.asElement())) {
                        checkBoxSelectionGroup.addItem(((CheckBox) o));
                    }
                }
            }
            DomUtil.unwrap(htmlElement);
            return checkBoxSelectionGroup;
        }

        @Override
        public String getId() {
            return "dn-checkbox-selection-group";
        }

        @Override
        public Class getClazz() {
            return CheckBoxSelectionGroup.class;
        }

    }

}

package com.dncomponents.client.components.checkbox;

import com.dncomponents.client.components.core.AbstractPluginHelper;
import com.dncomponents.client.components.core.BaseComponent;
import com.dncomponents.client.components.core.ComponentHtmlParser;
import com.dncomponents.client.components.core.entities.ItemId;
import com.dncomponents.client.dom.handlers.OnChangeHandler;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxView;
import com.dncomponents.client.views.core.ui.checkbox.CheckBoxViewSlots;
import com.google.gwt.user.client.ui.HasValue;
import elemental2.dom.Element;
import elemental2.dom.Event;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * @author nikolasavic
 */
public class CheckBox<T> extends AbstractCheckBox<T> implements HasValue<Boolean> {

    protected CheckBoxSelectionGroup<T> group;

    Boolean value;

    {
        setRenderer(new CheckBoxRenderer<T>() {
            @Override
            public void render(T t, CheckBoxViewSlots slots) {
                slots.getMainSlot().innerHTML = t + "";
            }
        });
    }

    public CheckBox(CheckBoxView view) {
        super(view);
        bind();
    }

    public CheckBox() {
        super(Ui.get().getCheckBoxView());
        bind();
    }

    public CheckBox(T userObject) {
        this();
        setUserObject(userObject);
        setLabel(userObject.toString());
    }

    public CheckBox(T userObject, CheckBoxSelectionGroup<T> group) {
        this();
        setGroup(group);
        setUserObject(userObject);
    }

    public CheckBox(String label) {
        this();
        setLabel(label);
    }

    protected void bind() {
        view.addOnChangeHandler(new OnChangeHandler() {
            @Override
            public void onChange(Event inputEvent) {
                if (!isEnabled())
                    return;
                fromView = true;
                setValue(view.isChecked(), true);
                if (group != null) {
                    group.setSelected(CheckBox.this, view.isChecked(), true);
                }
                fromView = false;
            }
        });
    }

    public void setIndeterminate(boolean b) {
        value = null;
        view.setIndeterminate(b);
    }

    public void setGroup(CheckBoxSelectionGroup<T> group) {
        this.group = group;
        view.setName(group.getGroupName());
        setRenderer(group.getDefaultRenderer());
    }

    public interface CheckBoxRenderer<T> extends BaseComponent.Renderer<T, CheckBoxViewSlots> {
    }

    public void setRenderer(CheckBoxRenderer<T> renderer) {
        super.setRendererBase(renderer);
    }

    @Override
    public CheckBoxViewSlots getViewSlots() {
        return super.getViewSlots();
    }

    //uibinder field
    protected String groupUiField;

    public static class CheckBoxHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static CheckBoxHtmlParser instance;
        //refer to ui-field of selection group
        public static final String GROUP = "group";
        public static final String VALUE = "value";
        public static final String DISABLED = "disabled";
//        public static final String LABEL = "label";

        private CheckBoxHtmlParser() {
            arguments.put(GROUP, Collections.emptyList());
            arguments.put(VALUE, Arrays.asList("true", "false"));
            arguments.put(DISABLED, Collections.emptyList());
//            arguments.put(LABEL, Collections.emptyList());
        }

        public static CheckBoxHtmlParser getInstance() {
            if (instance == null)
                return instance = new CheckBoxHtmlParser();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> templateElement) {
            CheckBoxView viewC = getView(CheckBox.class, htmlElement, templateElement);
            CheckBox<ItemId> checkBox;
            if (viewC != null)
                checkBox = new CheckBox(viewC);
            else
                checkBox = new CheckBox();

            setValueCh(htmlElement, checkBox);
            checkBox.setEnabled(!htmlElement.hasAttribute(DISABLED));
            checkBox.setRenderer(new CheckBoxRenderer<ItemId>() {
                @Override
                public void render(ItemId idItem, CheckBoxViewSlots slots) {
                    slots.getMainSlot().innerHTML = idItem.getContent();
                }
            });
            checkBox.setUserObject(getIdItem(htmlElement));
            checkBox.groupUiField = htmlElement.getAttribute(GROUP);
            replaceAndCopy(htmlElement, checkBox);
            return checkBox;
        }

        protected static void setValueCh(Element htmlElement, AbstractCheckBox checkBox) {
            String value = htmlElement.getAttribute(VALUE);
            if (value != null && (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"))) {
                checkBox.setValue(value.equalsIgnoreCase("true") ? true : false);
            }
        }

        @Override
        public String getId() {
            return "dn-checkbox";
        }

        @Override
        public Class getClazz() {
            return CheckBox.class;
        }

    }

}
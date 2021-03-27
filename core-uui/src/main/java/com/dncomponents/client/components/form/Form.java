package com.dncomponents.client.components.form;

import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.*;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.form.HasModelChangedHandlers;
import com.dncomponents.client.components.core.events.form.ModelChangedEvent;
import com.dncomponents.client.components.core.events.form.ModelChangedHandler;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.core.events.value.HasValueSelection;
import com.dncomponents.client.components.modal.Dialog;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import com.dncomponents.client.views.Ui;
import com.dncomponents.client.views.core.ui.form.FormUi;
import elemental2.dom.DomGlobal;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;
import elemental2.dom.NodeList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author nikolasavic
 */
public class Form<T> extends BaseComponent<T, FormUi> implements HasModelChangedHandlers<T> {

    private T model;
    private String submitButtonName = "Submit";

    List<FieldConfig<T, ?>> fieldConfigs = new ArrayList<>();
    List<FormItem<T, ?>> fields = new ArrayList<>();

    public Form() {
        this(Ui.get().getFormUi());
        init();
    }

    public Form(FormUi view) {
        super(view);
        init();
    }

    public boolean areFieldsValid() {
        boolean allValid = true;
        for (FormItem field : fields) {
            if (!field.isEditable()) continue;
            if (!field.isValid()) {
                if (allValid)
                    allValid = false;
            }
        }
        return allValid;
    }

    private void init() {
        view.getRootView().addSaveHandler(e -> {
            save();
        });
    }

    public boolean save() {
        if (areFieldsValid()) {
            for (FormItem field : fields) {
                if (!field.isEditable()) continue;
                field.setValueCmd.execute();
            }

            DomGlobal.console.log("Saved: " + getModel() + "");
            ModelChangedEvent.fire(this, getModel());
            return true;
        }
        return false;
    }

    public void addFormConfigs(FieldConfig<T, ?>... items) {
        for (FieldConfig<T, ?> formItem : items)
            this.fieldConfigs.add(formItem);
    }

    public void addFormConfigs(List<FieldConfig<T, ?>> list) {
        this.fieldConfigs.addAll(list);
    }

    public void setFormData(T model) {
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public void drawData() {
        fields.clear();
        for (FieldConfig<T, ?> config : fieldConfigs) {
            final CellEditor<?> cellEditor = config.getCellEditorFactory().getCellEditor(model);
            FormItem formItem = new FormItem(this);
            formItem.setFieldConfig(config);
            if (formItem.isEditable()) {
                formItem.setHasValue(cellEditor.getHasValue());
                formItem.setIsElement(cellEditor.getIsElement());
            }
            formItem.setModel(getModel());
            formItem.setValueFromModel();
            fields.add(formItem);
            view.getRootView().addItem(formItem.asElement());
        }
    }

    public void addFormItem(FormItem<T, ?> formItem) {
        this.fields.add(formItem);
        view.getRootView().addItem(formItem.asElement());
    }

    public List<FormItem<T, ?>> getFields() {
        return fields;
    }

    public List<FieldConfig<T, ?>> getFieldConfigs() {
        return fieldConfigs;
    }

    public FormItem<T, ?> getFormItemFromFieldConfig(FieldConfig fieldConfig) {
        for (FormItem<T, ?> field : fields) {
            if (field.getFieldConfig().equals(fieldConfig))
                return field;
        }
        return null;
    }

    public <M> M getValue(FieldConfig fieldConfig) {
        return (M) getFormItemFromFieldConfig(fieldConfig).getHasValue().getValue();
    }

    public void showInDialog(IsElement owner, Command cmd) {
        Dialog dialog = new Dialog();
        this.drawData();
        dialog.setContent(se -> se.appendChild(this.asElement()));
        dialog.setFooter(e -> e.appendChild(new Button(submitButtonName, event -> {
            if (save()) {
                dialog.hide();
                cmd.execute();
            }
        }).asElement()));
        this.showSubmitButton(false);
        dialog.show();
    }

    public void setSubmitButtonText(String text) {
        this.submitButtonName = text;
        view.getRootView().setSubmitButtonText(text);
    }

    public void showSubmitButton(boolean b) {
        this.view.getRootView().showSubmitButton(b);
    }


    @Override
    protected FormUi getView() {
        return super.getView();
    }


    @Override
    public HandlerRegistration addModelChangedHandler(ModelChangedHandler<T> handler) {
        return handler.addTo(asElement());
    }

    public static class FormHtmlParser extends AbstractPluginHelper implements ComponentHtmlParser {

        private static final String SUBMIT_BUTTON_TEXT = "submitText";
        private static final String LABEL = "label";
        private static final String HELPER_TEXT = "helperText";
        private static final String SUCCESS_TEXT = "successText";
        private static int FORM_ITEM_ID = 0;
        private static int FORM_ID = 0;

        private static Form.FormHtmlParser instance;

        private FormHtmlParser() {
            tags.put(CONTENT, Collections.emptyList());
            tags.put(ITEM, Collections.emptyList());
        }

        public static Form.FormHtmlParser getInstance() {
            if (instance == null)
                return instance = new Form.FormHtmlParser();
            return instance;
        }

        String id;

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            final HTMLElement wrapper = DomUtil.createElement("dn-form-after");
            if (htmlElement.getAttribute("ui-field") == null) {
                id = "form-" + FORM_ID++;
                htmlElement.setAttribute("ui-field", id);
                wrapper.setAttribute("for-field", id);
            }
            wrapper.setAttribute("for-field", htmlElement.getAttribute("ui-field"));
            DomUtil.wrap((HTMLElement) htmlElement, wrapper);

            Form form;
            FormUi ui = getView(FormUi.class, htmlElement, elements);
            if (ui != null)
                form = new Form(ui);
            else
                form = new Form();

            if (htmlElement.hasAttribute(SUBMIT_BUTTON_TEXT))
                form.setSubmitButtonText(htmlElement.getAttribute(CONTENT));

            NodeList<Element> elementsByTagName = htmlElement.getElementsByTagName(ITEM);
            for (int i = 0; i < elementsByTagName.length; i++) {
                FormItem formItem = parseItem((HTMLElement) elementsByTagName.getAt(i), form);
                form.addFormItem(formItem);
            }
            replaceAndCopy(htmlElement, form);
            if (id != null) {
                final Map elements1 = elements;
                elements1.put(id, form);
            }
            return form;
        }

        public FormItem parseItem(HTMLElement element, Form form) {
            final FormItem formItem = new FormItem(form);
            if (element.hasAttribute(LABEL)) {
                formItem.setLabel(element.getAttribute(LABEL));
            }
            if (element.hasAttribute(HELPER_TEXT)) {
                formItem.setHelperText(element.getAttribute(HELPER_TEXT));
            }
            if (element.hasAttribute(SUCCESS_TEXT)) {
                formItem.setSuccessText(element.getAttribute(SUCCESS_TEXT));
            }
            if (element.firstElementChild != null) {
                final Element firstElementChild = element.firstElementChild;
                formItem.setIsElement(() -> (HTMLElement) firstElementChild);
                if (firstElementChild.getAttribute("ui-field") == null)
                    firstElementChild.setAttribute("ui-field", "form-item-" + FORM_ITEM_ID++);
                formItem.hasValueUiField = firstElementChild.getAttribute("ui-field");
            }
            return formItem;
        }

        @Override
        public String getId() {
            return "dn-form";
        }

        @Override
        public Class getClazz() {
            return Form.class;
        }
    }

    public static class FormHtmlParserAfter extends AbstractPluginHelper implements ComponentHtmlParser {

        private static FormHtmlParserAfter instance;

        private FormHtmlParserAfter() {
        }

        public static FormHtmlParserAfter getInstance() {
            if (instance == null)
                return instance = new FormHtmlParserAfter();
            return instance;
        }

        @Override
        public BaseComponent parse(Element htmlElement, Map<String, ?> elements) {
            final String key = htmlElement.getAttribute("for-field");
            Form form = (Form) elements.get(key);
            for (FormItem field : ((Form<?>) form).fields) {
                if (field.hasValueUiField != null) {
                    final Object o1 = elements.get(field.hasValueUiField);
                    if (o1 instanceof HasValueSelection)
                        field.setHasValue(((HasValueSelection<?>) o1).getHasValue());
                    else if (o1 instanceof HasValue)
                        field.setHasValue((HasValue) o1);
                }
            }
            DomUtil.unwrap(htmlElement);
            return null;
        }

        @Override
        public String getId() {
            return "dn-form-after";
        }

        @Override
        public Class getClazz() {
            return Form.class;
        }
    }

}

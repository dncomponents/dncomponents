package com.dncomponents.client.components;

import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.html.HtmlComponent;
import com.dncomponents.client.components.modal.Dialog;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nikolasavic
 */
public class EditFormDialog<T> extends Dialog<T> {


    private HTMLElement mainPanel = DomUtil.createDiv();
    private List<ColumnConfig> configList;
    private T model;
    RowTable<T> cell;

    boolean autocommit = false;


    public EditFormDialog(RowTable<T> cell) {
        super();
        this.configList = cell.getOwner().getColumnConfigs();
        this.model = cell.getModel();
        this.cell = cell;
        init();
    }

    List<Command> fields = new ArrayList<>();
    List<HandlerRegistration> handlerRegistrationList = new ArrayList<>();

    private void init() {
        Button saveButton = new Button("Save", mouseEvent -> save());
        setHeader(e -> e.textContent = model.toString());
        if (!autocommit)
            setFooter(e -> e.appendChild(saveButton.asElement()));
        for (int i = 0; i < configList.size(); i++) {
            ColumnConfig cc = configList.get(i);
            if (cc.isEditable()) {
                IsElement title = new HtmlComponent("b", cc.getColumnName());
                Object value = cc.getFieldGetter().apply(model);
                HasValue valueElement = cell.getCells().get(i).getCellEditor().getHasValue();
                valueElement.setValue(value, false);
                final HandlerRegistration handlerRegistration = valueElement.addValueChangeHandler(event -> {
                    if (autocommit) {
                        cc.getFieldSetter().accept(model, event.getValue());
                        cell.draw();
                    }
                });
                handlerRegistrationList.add(handlerRegistration);
                fields.add(() -> cc.getFieldSetter().accept(model, valueElement.getValue()));
                IsElement element = (IsElement) valueElement;
                HTMLDivElement div = DomUtil.createDiv();
                div.appendChild(title.asElement());
                div.appendChild(element.asElement());
                mainPanel.appendChild(div);
            }
            setContent(e -> e.appendChild(mainPanel));
        }
    }

    public void save() {
        if (!autocommit) {
            fields.forEach(Command::execute);
            cell.draw();
        }
        hide();
    }

    private void clearAll() {
        fields.clear();
        for (HandlerRegistration handlerRegistration : handlerRegistrationList) {
            handlerRegistration.removeHandler();
        }
    }

    @Override
    public void hide() {
        super.hide();
        clearAll();
    }

    public void setAutocommit(boolean autocommit) {
        this.autocommit = autocommit;
    }

    public boolean isAutocommit() {
        return autocommit;
    }
}
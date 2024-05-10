/*
 * Copyright 2024 dncomponents
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dncomponents.client.components;

import com.dncomponents.client.components.button.Button;
import com.dncomponents.client.components.core.events.Command;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.form.ModelChangedEvent;
import com.dncomponents.client.components.core.events.value.HasValue;
import com.dncomponents.client.components.html.HtmlComponent;
import com.dncomponents.client.components.modal.Dialog;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.IsElement;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;


public class EditFormDialog<T> extends Dialog<T> {

    private HTMLElement mainPanel = DomUtil.createDiv();
    private List<ColumnConfig> configList;
    private T model;
    RowTable<T> cell;
    BiConsumer<EditFormDialog<T>, RowTable<T>> dialogConsumer;
    boolean autocommit = false;
    private Button saveButton = new Button("Save", mouseEvent -> save());
    private boolean addMode;

    public EditFormDialog(RowTable<T> cell) {
        super();
        setUserObject(cell.getModel());
        this.configList = cell.getOwner().getColumnConfigs();
        this.model = cell.getModel();
        this.cell = cell;
        this.dialogConsumer = cell.getOwner().getDialogConsumer();
        init();
    }


    List<Command> fields = new ArrayList<>();
    List<HandlerRegistration> handlerRegistrationList = new ArrayList<>();

    private void init() {
        setHeader(e -> e.textContent = model.toString());
        if (dialogConsumer != null)
            dialogConsumer.accept(this, this.cell);
        if (!autocommit)
            setFooter(e -> e.appendChild(saveButton.asElement()));
        for (int i = 0; i < configList.size(); i++) {
            ColumnConfig cc = configList.get(i);
            if (cc.isEditable()) {
                IsElement title = new HtmlComponent("b", cc.getName());
                Object value = cc.getFieldGetter().apply(model);
                HasValue valueElement = cell.getCells().get(i).getCellEditor().getHasValue();
                valueElement.setValue(value, false);
                final HandlerRegistration handlerRegistration = valueElement.addValueChangeHandler(event -> {
                    if (autocommit) {
                        if (cc.getFieldSetter() == null)
                            throw new NullPointerException("Please define field setter for the column " + cc.getName());
                        cc.getFieldSetter().accept(model, event.getValue());
                        cell.draw();
                    }
                });
                handlerRegistrationList.add(handlerRegistration);
                fields.add(() -> cc.getFieldSetter().accept(model, valueElement.getValue()));
                IsElement element = (IsElement) valueElement;
                HTMLElement div = DomUtil.createDiv();
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
            ModelChangedEvent.fire(getCell().getOwner(), getCell().model);
        }
        if (isAddMode()) {
            getCell().getOwner().insertRow(getUserObject(), 0);
            getCell().getOwner().drawData();
        }
        hide();
    }

    public RowTable<T> getCell() {
        return cell;
    }

    private void clearAll() {
        fields.clear();
        for (HandlerRegistration handlerRegistration : handlerRegistrationList) {
            handlerRegistration.removeHandler();
        }
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }

    public boolean isAddMode() {
        return addMode;
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

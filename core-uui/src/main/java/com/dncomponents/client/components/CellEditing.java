/*
 * Copyright 2023 dncomponents
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

import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.components.core.events.cell.CellEditingStartedEvent;
import com.dncomponents.client.components.core.events.cell.CellEditingStoppedEvent;
import com.dncomponents.client.components.core.events.cell.CellValueChangedEvent;
import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.core.events.validator.CanShowError;
import com.dncomponents.client.components.core.events.validator.CanShowSuccess;
import com.dncomponents.client.components.core.validation.ValidationException;
import com.dncomponents.client.components.core.validation.Validator;
import com.dncomponents.client.components.table.TableCell;
import com.dncomponents.client.components.textbox.HasValueParser;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.dom.handlers.*;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.KeyboardEvent;

/**
 * Defines cell editing behavior.
 * Default trigger event is double click
 * To change this behavior add cell handler to cell owner and start editing programmatically.
 *
 * @author nikolasavic
 */
public class CellEditing<T, M> {

    protected Validator<M> validator;
    final boolean autoCommit = true;
    BaseCellView cellView;
    CellEditor<M> cellEditor;
    boolean stopBlur;
    BaseCell<T, M> cell;
    boolean isEditing = false;
    HandlerRegistration valueChangeRegistration;
    protected boolean hasError;
    public boolean fromRow;

    public CellEditing(BaseCell c) {
        this.cell = c;
        if (cell instanceof TableCell)
            fromRow = ((TableCell<T, M>) cell).getRowTable().fromRow;
        cellView = cell.getCellView();
        validator = cell.getCellConfig().getValidator();
        DomUtil.addHandler(cell.asElement(), (OnFocusHandler) focusEvent -> {
            if (isEditing) {
                focus();
                cellEditor.startEditing();
            }
        });
    }

    public boolean hasChanges() {
        return cellEditor != null && cellEditor.getHasValue() != null && cellEditor.getHasValue().getValue() != cell.cellConfig.getFieldGetter().apply(cell.model);
    }

    void saveChanges() {
        cell.getCellConfig().getFieldSetter().accept(cell.model, cellEditor.getHasValue().getValue());
        newValue = cell.cellConfig.getFieldGetter().apply(cell.model);
        stopEditing();
    }

    void editCell() {
        startEditing();
        addToValuePanel();
        stopPropagation();
        if (!fromRow)
            focus();
        addKeyUpHandlerToEditComponent(CellEditing.this::onKeyUpEvent);
        addBlurHandlerToEditComponent(focusEvent -> {
            if (!stopBlur && !fromRow) {
                onBlurEvent();
            }
        });
        cellEditor.setEndEditingHandler(() -> stopEditing());
    }

    private void onBlurEvent() {
        asElement(cellEditor).remove();
        cellEditor.getHasValue().setValue(null);
        stopEditing();
    }

    public void stopEditing() {
        valueChangeRegistration.removeHandler();
        cell.draw();
        isEditing = false;
        CellEditingStoppedEvent.fire(cell.getOwner(), cell);
    }

    public void startEditing() {
        isEditing = true;
        final M cellValue = cell.getCellConfig().getFieldGetter().apply(cell.model);
        cellEditor = cell.getCellEditor();
        if (cellEditor == null) {
            throw new NullPointerException("Define edit component for the type.");
        }
        cellEditor.getHasValue().setValue(cellValue, false);
        valueChangeRegistration = cellEditor.getHasValue().addValueChangeHandler(event -> {
            onValueChangedEvent();
        });
        cellEditor.showErrorMessage(null);
        if (!fromRow)
            cellEditor.startEditing();
        cell.getOwner().fireEvent(new CellEditingStartedEvent<>(cell));
    }

    M originalValue = null;
    M newValue = null;

    private void onValueChangedEvent() {
        stopBlur = true;
        final CellValueChangedEvent<T> cellEvent = new CellValueChangedEvent<>(cell);
        try {
            validateEnteredValue();
            if (autoCommit) {
                originalValue = cell.cellConfig.getFieldGetter().apply(cell.model);
                if (fromRow)
                    return;
                cellEvent.setOldValue(originalValue);
                cell.getCellConfig().getFieldSetter().accept(cell.model, cellEditor.getHasValue().getValue());
                newValue = cell.cellConfig.getFieldGetter().apply(cell.model);
                cellEvent.setNewValue(newValue);
                cell.getOwner().fireEvent(cellEvent);
            }
            stopEditing();
        } catch (Exception ex) {

        }
        stopBlur = false;
    }

    void setValueChangedStyle(boolean b) {
        cellView.setValueChangedStyle(b);
    }

    void setErrorStyle(boolean b) {
        cellView.setErrorStyle(b);
    }

    void revertValueBeforeEdit() {
        cell.cellConfig.getFieldSetter().accept(cell.model, originalValue);
        cell.draw();
    }

    private void onKeyUpEvent(KeyboardEvent event) {
        stopBlur = true;
        if (event.code.equals("Escape") && fromRow) {
            onBlurEvent();
        }
        stopBlur = false;
    }

    void addToValuePanel() {
        cellView.setToValuePanel(asElement(cellEditor));
    }

    void addBlurHandlerToEditComponent(OnBlurHandler handler) {
        if (cellEditor.getIsElement() instanceof HasBlurHandlers) {
            ((HasBlurHandlers) cellEditor.getIsElement()).addBlurHandler(handler);
        } else {
            DomUtil.addHandler(cellEditor.getIsElement().asElement(), handler);
        }
    }

    void addKeyUpHandlerToEditComponent(KeyUpHandler handler) {
        asElement(cellEditor).addEventListener(KeyUpHandler.TYPE, handler);
    }

    void focus() {
        cellEditor.getFocusable().setFocus(true);
    }

    void stopPropagation() {
        asElement(cellEditor).addEventListener(ClickHandler.TYPE, Event::stopPropagation);
        asElement(cellEditor).addEventListener(DoubleClickHandler.TYPE, Event::stopPropagation);
        asElement(cellEditor).addEventListener(KeyDownHandler.TYPE, Event::stopPropagation);
    }

    private Element asElement(CellEditor cellEdit) {
        return cellEdit.getIsElement().asElement();
    }

    private HasValueParser asValueParser(CellEditor obj) {
        if (obj.getHasValue() instanceof HasValueParser)
            return ((HasValueParser) obj.getHasValue());
        return null;
    }

    void validateEnteredValue() throws ValidationException {
        hasError = false;
        if (validator != null) {
            try {
                validator.validate(cellEditor.getHasValue().getValue());
                cellEditor.showErrorMessage(null);
                cellView.setErrorStyle(false);
                setSuccess(true);
                cellView.showSuccessMessage(cell.cellConfig.getSuccessText());
            } catch (ValidationException ex) {
                setError(true);
                cellView.showErrorMessage(ex.getMessage());
                throw ex;
            }
        }
    }


    private void showError(String error) {
        if (cellEditor.getHasValue() instanceof CanShowError)
            cellEditor.showErrorMessage(error);
    }

    private void setError(boolean error) {
        if (cellEditor.getHasValue() instanceof CanShowError)
            ((CanShowError) cellEditor.getHasValue()).setErrorStyle(error);
    }

    private void showSuccess(String success) {
        if (cellEditor.getHasValue() instanceof CanShowSuccess && success != null) {
            ((CanShowSuccess) cellEditor.getHasValue()).showSuccessMessage(success);
        }
    }

    private void setSuccess(boolean success) {
        if (cellEditor.getHasValue() instanceof CanShowSuccess) {
            ((CanShowSuccess) cellEditor.getHasValue()).setSuccessStyle(success);
        }
    }

    public boolean hasError() {
        return this.hasError;
    }

    public boolean isEditing() {
        return isEditing;
    }

}

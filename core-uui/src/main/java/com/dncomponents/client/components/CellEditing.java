package com.dncomponents.client.components;

import com.dncomponents.client.dom.handlers.*;
import com.dncomponents.client.components.core.CellEditor;
import com.dncomponents.client.components.events.CellEditEvent;
import com.dncomponents.client.components.events.CellValidationException;
import com.dncomponents.client.components.events.CellValidator;
import com.dncomponents.client.components.core.events.focus.HasBlurHandlers;
import com.dncomponents.client.components.textbox.HasValueParser;
import com.dncomponents.client.dom.DomUtil;
import com.dncomponents.client.views.core.pcg.cell.BaseCellView;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import elemental2.dom.Element;
import elemental2.dom.Event;
import elemental2.dom.KeyboardEvent;

import java.text.ParseException;

/**
 * Defines cell editing behavior.
 * Default trigger event is double click
 * To change this behavior add cell handler to cell owner and start editing programmatically.
 *
 * @author nikolasavic
 */
class CellEditing<T, M> {

    protected CellValidator<T, M> validator;
    private final boolean autoCommit = true;
    BaseCellView cellView;
    CellEditor<M> cellEditor;
    private boolean stopBlur;
    private BaseCell<T, M> cell;
    private boolean isEditing = false;
    HandlerRegistration valuChangeRegistration;

    public CellEditing(BaseCell c) {
        this.cell = c;
        cellView = cell.getCellView();
    }

    void editCell() {
        startEditing();
        addToValuePanel();
        stopPropagation();
        focus();
        addKeyUpHandlerToEditComponent(CellEditing.this::onKeyUpEvent);
        addBlurHandlerToEditComponent(focusEvent -> {
            if (!stopBlur) {
                onBlurEvent();
            }
        });
        cellEditor.setEndEditingHandler(() -> stopEditing());
    }

    private void onBlurEvent() {
        asElement(cellEditor).remove();
        valuChangeRegistration.removeHandler();
        cellEditor.getHasValue().setValue(null);
        stopEditing();
    }

    void stopEditing() {
        cell.draw();
        isEditing = false;
    }

    private void startEditing() {
        isEditing = true;
        final M cellValue = cell.getCellConfig().getFieldGetter().apply(cell.model);
        cellEditor = cell.getCellEditor();
        if (cellEditor == null) {
            throw new NullPointerException("Define edit component for the type.");
        }
        cellEditor.getHasValue().setValue(cellValue, false);
        valuChangeRegistration = cellEditor.getHasValue().addValueChangeHandler(event -> onValueChangedEvent());
        cellEditor.startEditing();
    }

    private M originalValue = null;

    private void onValueChangedEvent() {
        stopBlur = true;
        try {
            validateEnteredValue();
            if (autoCommit) { //todo autocommit else?
                originalValue = cell.cellConfig.getFieldGetter().apply(cell.model);
                cell.getCellConfig().getFieldSetter().accept(cell.model, cellEditor.getHasValue().getValue());
                cell.getOwner().fireEvent(new CellEditEvent<T>(cell, cellServerResponse));
            }
//            cellView.setValueChangedStyle(true);
            stopEditing();
        } catch (Exception ex) {
            cellView.setErrorStyle(true);
        }
        stopBlur = false;
    }

    private CellServerResponse cellServerResponse = new CellServerResponse() {
        @Override
        public void success(boolean b, String message) {
            if (b) {
                cellView.setValueChangedStyle(true);
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        cellView.setValueChangedStyle(false);
                    }
                };
                timer.schedule(1000);
            } else {
                cellView.setErrorStyle(true);
                Timer timer = new Timer() {
                    @Override
                    public void run() {
                        cellView.setErrorStyle(false);
                    }
                };
                timer.schedule(1000);
            }
        }

        @Override
        public void revertLastValue() {
            cell.cellConfig.getFieldSetter().accept(cell.model, originalValue);
            cell.draw();
        }
    };

    private void onKeyUpEvent(KeyboardEvent event) {
        stopBlur = true;
        if (event.code.equals("Escape")) {
            onBlurEvent();
        } else {
            try {
                validateAsYouType();
                cellView.setErrorStyle(false);
            } catch (Exception e) {
                cellView.setErrorStyle(true);
            }
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

    private HasValueParser asValueParser(Object obj) {
        if (obj instanceof HasValueParser)
            return ((HasValueParser) obj);
        return null;
    }

    private void validateAsYouType() throws ParseException, CellValidationException {
        if (validator != null) {
            if (asValueParser(cellEditor) != null) {
                M value = (M) asValueParser(cellEditor).getValueOrThrow();
                validator.validate(value, cell.getModel());
            }
        }
    }

    private void validateEnteredValue() throws CellValidationException {
        if (validator != null) {
            if (asValueParser(cellEditor) != null)
                validator.validate(cellEditor.getHasValue().getValue(), cell.getModel());
        }
    }

    public boolean isEditing() {
        return isEditing;
    }

    //for testing only
    void setCell(BaseCell cell) {
        this.cell = cell;
        cellView = cell.getCellView();
    }
}
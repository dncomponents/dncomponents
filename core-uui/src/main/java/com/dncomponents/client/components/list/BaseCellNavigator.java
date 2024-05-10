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

package com.dncomponents.client.components.list;

import com.dncomponents.client.components.AbstractCellComponent;
import com.dncomponents.client.components.BaseCell;
import com.dncomponents.client.components.core.events.HandlerRegistration;
import com.dncomponents.client.dom.handlers.ClickHandler;
import com.dncomponents.client.dom.handlers.KeyDownHandler;
import elemental2.dom.KeyboardEvent;
import elemental2.dom.MouseEvent;


abstract class BaseCellNavigator {

    //TODO on enter select
    protected AbstractCellComponent owner;
    protected Object currentFocusedModel;
    protected Object lastFocusedModel;

    protected HandlerRegistration keyDownHandlerRegistration;
    protected HandlerRegistration clickHandlerRegistration;

    protected HasNavigationHandler hasNavigationHandler;
    protected Handler handler;

    public BaseCellNavigator(AbstractCellComponent owner, HasNavigationHandler hasNavigationHandler) {
        this.owner = owner;
        this.hasNavigationHandler = hasNavigationHandler;
        setHandlers();
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setValueClick(Object model, MouseEvent mouseEvent) {
//        if (cell.isFocusable()) {
        setVal(model);
        if (currentFocusedModel != lastFocusedModel)
            handler.onClickEvent(currentFocusedModel, lastFocusedModel, mouseEvent);
        else
            handler.onClickEventEquals(currentFocusedModel, mouseEvent);
//        }
    }

//    private boolean isNavKeys(KeyboardEvent keyboardEvent) {
//        return (Key.ArrowDown.match(keyboardEvent) || Key.ArrowUp.match(keyboardEvent)
//                || Key.ArrowLeft.match(keyboardEvent) || Key.ArrowRight.match(keyboardEvent));
//    }

    public void setVal(Object model) {
        setVal(model, false, null);
    }


    public void setVal(Object model, boolean nav, KeyboardEvent keyboardEvent) {
        lastFocusedModel = currentFocusedModel;
        currentFocusedModel = model;
        if (lastFocusedModel != null)
            setCellFocused(lastFocusedModel, false);
        if (nav && keyboardEvent != null)
            handler.onKeyFocused(currentFocusedModel, lastFocusedModel, keyboardEvent);

        setCellFocused(currentFocusedModel, true);
    }

    private void setCellFocused(Object model, boolean b) {
        BaseCell cell = owner.getRowCell(model);
        if (cell != null && cell.isFocusable())
            cell.setFocus(b);
    }

    protected abstract void moveCellUp(KeyboardEvent keyboardEvent);

    protected abstract void moveCellDown(KeyboardEvent keyboardEvent);

    protected abstract void moveCellLeft(KeyboardEvent keyboardEvent);

    protected abstract void moveCellRight(KeyboardEvent keyboardEvent);

    public void removeHandlers() {
        if (clickHandlerRegistration != null)
            clickHandlerRegistration.removeHandler();
        if (keyDownHandlerRegistration != null)
            keyDownHandlerRegistration.removeHandler();
    }

    public void setHandlers() {
        keyDownHandlerRegistration = hasNavigationHandler.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyboardEvent keyboardEvent) {
                if (currentFocusedModel == null) return;
                if (keyboardEvent.key.equals("ArrowDown")) {
                    moveCellUp(keyboardEvent);
                } else if (keyboardEvent.key.equals("ArrowUp")) {
                    moveCellDown(keyboardEvent);
                } else if (keyboardEvent.key.equals("ArrowLeft")) {
                    moveCellLeft(keyboardEvent);
                } else if (keyboardEvent.key.equals("ArrowRight")) {
                    moveCellRight(keyboardEvent);
                }
            }
        });
        clickHandlerRegistration = owner.addCellHandler(new ClickHandler() {
            @Override
            public void onClick(MouseEvent mouseEvent) {
                setValueClick(owner.getCell(mouseEvent).getModel(), mouseEvent);
            }
        });
        owner.addCellHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyboardEvent event) {
                if (event.code.equals("Enter")) {
                    setValueClick(owner.getCell(event).getModel(), new MouseEvent(""));
                }

            }
        });
    }

    interface Handler {
        void onKeyFocused(Object currentFocusedModel, Object lastFocusedModel, KeyboardEvent event);

        void onClickEvent(Object currentFocusedModel, Object lastFocusedModel, MouseEvent event);

        void onClickEventEquals(Object currentFocusedModel, MouseEvent mouseEvent);
    }

}
